package com.firman.ecommerce.foodshop.fragment.cart;

import android.content.Context;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.SessionHandler;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.databinding.DialogConfirmationBinding;
import com.firman.ecommerce.foodshop.pojo.ProductCart;
import com.firman.ecommerce.foodshop.widget.CustomDialog;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/17/2018.
 */
public class CartViewModel extends BaseViewModel implements DialogConfirmationViewModel.DialogConfirmationListener, SwipeRefreshLayout.OnRefreshListener {

    private final CartListener cartListener;
    private ObservableInt totalItems = new ObservableInt();
    private ObservableLong totalPrice = new ObservableLong();

    private CustomDialog customDialog;
    private DialogConfirmationViewModel vm;
    private Disposable confirmDisposable;


    public CartViewModel(Context context, CartListener cartListener) {
        super(context);
        this.cartListener = cartListener;
        vm = new DialogConfirmationViewModel(this);
        DialogConfirmationBinding binding = DialogConfirmationBinding.inflate(LayoutInflater.from(context), null);
        binding.setVm(vm);
        binding.executePendingBindings();
        customDialog = CustomDialog.get(context)
                .title("Confirm Your Order")
                .cancelable(false)
                .addView(binding.getRoot());

    }

    public ObservableInt getTotalItems() {
        return totalItems;
    }

    public ObservableLong getTotalPrice() {
        return totalPrice;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
        vm.setTotalItems(totalItems);

    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice.set(totalPrice);
        vm.setTotalPrice(totalPrice);
    }

    public void refresh() {
        getCompositeDisposable().add(getCarts().subscribe(objects -> {
            if (cartListener != null)
                cartListener.onRefresh(objects);
        }, throwable -> {
            throwable.printStackTrace();
        }));
    }

    private Observable<List<Object>> getCarts() {
        return Rx2AndroidNetworking.get(ConstantNetwork.CART)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Object> carts = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("DataRow");
                    for (int i=0;i<array.length();i++) {
                        JSONObject item = array.getJSONObject(i);

                        if (!carts.contains(item.getString("C_SELLER_NAME")))
                            carts.add(item.getString("C_SELLER_NAME"));
                        carts.add(ProductCart.fromJson(item));
                    }

                    return carts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onProcessClick() {

        vm.setN_SALDO(getGeneralSession().get(SessionHandler.KEY_NSALDO, 0L));

        if (customDialog.isShow()) {
            customDialog.dismiss();
        } else {
            customDialog.show();
        }
    }

    @Override
    public void onCancel() {
        customDialog.dismiss();
    }

    @Override
    public void onConfirmation() {
        if (confirmDisposable != null)
            confirmDisposable.dispose();

        cartListener.onLoad();

        confirmDisposable = Rx2AndroidNetworking.post(ConstantNetwork.PROCESS_TRANS)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> jsonObject.getBoolean("success") && jsonObject.getInt("LastInsertId") != 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        refresh();
                        customDialog.dismiss();
                        showSuccessDialog();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Error process transaction, please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void showSuccessDialog() {
        CustomDialog.get(getContext())
                .title("Success")
                .message("Transaction Confirmed.")
                .addPositiveButton("OK", null);
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    public interface CartListener {
        void onLoad();
        void onRefresh(List<Object> objects);
        void onError(Throwable cause);
    }
}
