package com.firman.ecommerce.foodshop.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Bitmap;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.base.BaseRecyclerAdapter;

/**
 * Created by Firman on 11/22/2018.
 */
public class Seller extends BaseObservable {

    private String C_SELLER_ID;
    private String C_SELLER_NAME;
    private String C_SELLER_ALIAS;
    private String C_SELLER_ADDR;
    private boolean C_SELLER_STATUS;
    private String C_SELLER_MAIL;
    private String C_SELLER_PHONE;
    private String C_SELLER_BACKGROUND_IMAGE;
    private String C_SELLER_PROFILE_IMAGE;

    private Bitmap C_BACKGROUND_IMAGE;
    private Bitmap C_PROFILE_IMAGE;


    private int totalItems;


    @Bindable
    public String getC_SELLER_ID() {
        return C_SELLER_ID;
    }

    @Bindable
    public String getC_SELLER_NAME() {
        return C_SELLER_NAME;
    }

    @Bindable
    public String getC_SELLER_ALIAS() {
        return C_SELLER_ALIAS;
    }

    @Bindable
    public String getC_SELLER_ADDR() {
        return C_SELLER_ADDR;
    }

    @Bindable
    public boolean isC_SELLER_STATUS() {
        return C_SELLER_STATUS;
    }

    @Bindable
    public String getC_SELLER_MAIL() {
        return C_SELLER_MAIL;
    }

    @Bindable
    public String getC_SELLER_PHONE() {
        return C_SELLER_PHONE;
    }

    @Bindable
    public String getC_SELLER_BACKGROUND_IMAGE() {
        return C_SELLER_BACKGROUND_IMAGE;
    }

    @Bindable
    public String getC_SELLER_PROFILE_IMAGE() {
        return C_SELLER_PROFILE_IMAGE;
    }

    @Bindable
    public Bitmap getC_BACKGROUND_IMAGE() {
        return C_BACKGROUND_IMAGE;
    }

    @Bindable
    public Bitmap getC_PROFILE_IMAGE() {
        return C_PROFILE_IMAGE;
    }

    @Bindable
    public int getTotalItems() {
        return totalItems;
    }

    public void setC_SELLER_ID(String c_SELLER_ID) {
        C_SELLER_ID = c_SELLER_ID;
        notifyPropertyChanged(BR.c_SELLER_ID);
    }

    public void setC_SELLER_NAME(String c_SELLER_NAME) {
        C_SELLER_NAME = c_SELLER_NAME;
        notifyPropertyChanged(BR.c_SELLER_NAME);
    }

    public void setC_SELLER_ALIAS(String c_SELLER_ALIAS) {
        C_SELLER_ALIAS = c_SELLER_ALIAS;
        notifyPropertyChanged(BR.c_SELLER_ALIAS);
    }

    public void setC_SELLER_ADDR(String c_SELLER_ADDR) {
        C_SELLER_ADDR = c_SELLER_ADDR;
        notifyPropertyChanged(BR.c_SELLER_ADDR);
    }

    public void setC_SELLER_STATUS(boolean c_SELLER_STATUS) {
        C_SELLER_STATUS = c_SELLER_STATUS;
        notifyPropertyChanged(BR.c_SELLER_STATUS);
    }

    public void setC_SELLER_MAIL(String c_SELLER_MAIL) {
        C_SELLER_MAIL = c_SELLER_MAIL;
        notifyPropertyChanged(BR.c_SELLER_MAIL);
    }

    public void setC_SELLER_PHONE(String c_SELLER_PHONE) {
        C_SELLER_PHONE = c_SELLER_PHONE;
        notifyPropertyChanged(BR.c_SELLER_PHONE);
    }

    public void setC_SELLER_BACKGROUND_IMAGE(String c_SELLER_BACKGROUND_IMAGE) {
        C_SELLER_BACKGROUND_IMAGE = c_SELLER_BACKGROUND_IMAGE;
        notifyPropertyChanged(BR.c_BACKGROUND_IMAGE);
    }

    public void setC_SELLER_PROFILE_IMAGE(String c_SELLER_PROFILE_IMAGE) {
        C_SELLER_PROFILE_IMAGE = c_SELLER_PROFILE_IMAGE;
        notifyPropertyChanged(BR.c_PROFILE_IMAGE);
    }

    public void setC_BACKGROUND_IMAGE(Bitmap c_BACKGROUND_IMAGE) {
        C_BACKGROUND_IMAGE = c_BACKGROUND_IMAGE;
        notifyPropertyChanged(BR.c_BACKGROUND_IMAGE);
    }

    public void setC_PROFILE_IMAGE(Bitmap c_PROFILE_IMAGE) {
        C_PROFILE_IMAGE = c_PROFILE_IMAGE;
        notifyPropertyChanged(BR.c_PROFILE_IMAGE);
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        notifyPropertyChanged(BR.totalItems);
    }
}
