package com.firman.ecommerce.foodshop.fragment.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.pojo.ProductCart;
import com.firman.ecommerce.foodshop.widget.CustomDialog;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Firman on 12/1/2018.
 */
public class ListProductCartViewModel extends BaseViewModel {

    public static final String TAG = ListProductCartViewModel.class.getSimpleName();

    private final ProductCart product;
    private final int pos;
    private final OnDeleteListener listener;

    public ListProductCartViewModel(Context context, ProductCart productCart, int pos, OnDeleteListener listener) {
        super(context);
        this.product = productCart;
        this.pos = pos;
        this.listener=listener;
    }

    public ProductCart getProduct() {
        return product;
    }

    public TextWatcher qtyChange() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable sequence) {
                if (TextUtils.isEmpty(sequence.toString().trim()) || sequence.toString().trim().length() == 0 ||
                        sequence.toString().trim().equals("0")) {
                    product.setN_BOOK(1);
                } else if (sequence.toString().trim().length() > 1 && sequence.toString().trim().startsWith("0")) {
                    product.setN_BOOK(Long.valueOf(sequence.toString().trim().substring(1)));
                } else if (!String.valueOf(product.getN_BOOK()).equals(sequence.toString().trim()) &&
                        sequence.toString().trim().length() > 0) {
                    product.setN_BOOK(Long.valueOf(sequence.toString().trim()));
                }
                update();
            }
        };
    }

    public TextWatcher notesChange() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable sequence) {
                String notes = sequence.toString().trim();
                if (product.getV_NOTES() == null || !product.getV_NOTES().equals(notes))
                    product.setV_NOTES(notes);
                update();
            }
        };
    }

    public void onAdd() {
        product.setN_BOOK(product.getN_BOOK() + 1);
    }

    private void update() {
        AndroidNetworking.forceCancel(TAG);
        Rx2AndroidNetworking.post(ConstantNetwork.CART)
                .addBodyParameter("C_USERNAME", getGeneralSession().getProfile().getC_USERNAME())
                .addBodyParameter("N_ITENO", product.getN_ITENO())
                .addBodyParameter("N_BOOK", String.valueOf(product.getN_BOOK()))
                .addBodyParameter("V_NOTES", product.getV_NOTES())
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            product.assignJson(response);
                            if (listener != null)
                                listener.onChange(pos);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void onRemove() {
        if (product.getN_BOOK() == 1) {
            CustomDialog customDialog = CustomDialog.get(getContext())
                    .title("Are You Sure?")
                    .message(String.format(Locale.getDefault(), "Delete %s from cart?", product.getV_ITNAM()));
            customDialog.addPositiveButton("Yes", (dialog, which) -> {
                onDelete();
            });
            customDialog.show();
            return;
        }
        product.setN_BOOK(product.getN_BOOK() - 1);
    }

    public void onDelete() {
        AndroidNetworking.forceCancel(TAG);
        Rx2AndroidNetworking.post(ConstantNetwork.CART)
                .addBodyParameter("C_USERNAME", getGeneralSession().getProfile().getC_USERNAME())
                .addBodyParameter("N_ITENO", product.getN_ITENO())
                .addBodyParameter("N_BOOK", String.valueOf(product.getN_BOOK()))
                .addBodyParameter("L_DELETE", String.valueOf(1))
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                if (listener != null)
                                    listener.onDelete(pos);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public interface OnDeleteListener {
        void onChange(int pos);
        void onDelete(int pos);
    }
}
