package com.firman.ecommerce.foodshop.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Firman on 12/5/2018.
 */
public class History extends BaseObservable {

    private String N_TRX_ID;
    private int totalItem;
    private long totalPrice;
    private Date D_TIME;
    private String C_SELLER_NAME;

    @Bindable
    public String getN_TRX_ID() {
        return N_TRX_ID;
    }

    @Bindable
    public int getTotalItem() {
        return totalItem;
    }

    @Bindable
    public long getTotalPrice() {
        return totalPrice;
    }

    @Bindable
    public Date getD_TIME() {
        return D_TIME;
    }

    @Bindable
    public String getC_SELLER_NAME() {
        return C_SELLER_NAME;
    }

    public void setN_TRX_ID(String n_TRX_ID) {
        N_TRX_ID = n_TRX_ID;
        notifyPropertyChanged(BR.n_TRX_ID);
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
        notifyPropertyChanged(BR.totalItem);
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
        notifyPropertyChanged(BR.totalPrice);
    }

    public void setD_TIME(Date d_TIME) {
        D_TIME = d_TIME;
        notifyPropertyChanged(BR.d_TIME);
    }

    public void setC_SELLER_NAME(String c_SELLER_NAME) {
        C_SELLER_NAME = c_SELLER_NAME;
        notifyPropertyChanged(BR.c_SELLER_NAME);
    }

    public static History fromJson(JSONObject jsonObject) throws JSONException, ParseException {
        History history = new History();
        history.setN_TRX_ID(jsonObject.getString("N_TRX_ID"));
        history.setTotalItem(jsonObject.getInt("totalItem"));
        history.setTotalPrice(jsonObject.getLong("totalPrice"));
        history.setD_TIME(Utils.parseDate(jsonObject.getString("D_TIME"), "yyyy-MM-dd HH:mm:ss"));
        history.setC_SELLER_NAME(jsonObject.getString("C_SELLER_NAME"));

        return history;
    }
}
