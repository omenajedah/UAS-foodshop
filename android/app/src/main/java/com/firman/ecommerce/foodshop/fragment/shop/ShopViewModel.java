package com.firman.ecommerce.foodshop.fragment.shop;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.pojo.Product;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/17/2018.
 */
public class ShopViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ShopViewModel.class.getSimpleName();

    private final ShopListener shopListener;
    private Disposable disposable;

    public ShopViewModel(Context context, ShopListener shopListener) {
        super(context);
        this.shopListener = shopListener;
    }

    public void refresh() {
        dispose();

        shopListener.onLoad();

        disposable = getProduct().subscribe(product -> {
            if (shopListener != null)
                shopListener.onProductAdded(product);
        }, throwable -> {
            Log.d(TAG, "Error = "+throwable.toString());
            if (shopListener != null)
                shopListener.onError(throwable);
        }, () -> {
            if (shopListener != null)
                shopListener.onAddedCompleted();
        });

        getCompositeDisposable().add(disposable);
    }

    private Observable<List<Product>> getProduct() {

        return Rx2AndroidNetworking.get(ConstantNetwork.PRODUCT)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<Product> productList = new ArrayList<>();

                    if (jsonObject.getInt("RowCount") < 0)
                        throw new Exception("Maaf tidak ada data");

                    JSONArray productArray = jsonObject.getJSONArray("DataRow");
                    for (int i=0;i<productArray.length();i++) {
                        productList.add(Product.fromJson(productArray.getJSONObject(i)));
                    }
                    return productList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public interface ShopListener {
        void onLoad();
        void onProductAdded(List<Product> products);
        void onAddedCompleted();
        void onError(Throwable cause);
    }
}
