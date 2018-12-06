package com.firman.ecommerce.foodshop.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.firman.ecommerce.foodshop.BR;

/**
 * Created by Firman on 11/30/2018.
 */
public class Person extends BaseObservable {

    private String C_USERNAME;
    private String C_PASSWORD;
    private String C_NAME;
    private String C_ADDRESS;
    private String C_MAIL;
    private String C_PHONE;

    @Bindable
    public String getC_USERNAME() {
        return C_USERNAME;
    }

    @Bindable
    public String getC_PASSWORD() {
        return C_PASSWORD;
    }

    @Bindable
    public String getC_NAME() {
        return C_NAME;
    }

    @Bindable
    public String getC_ADDRESS() {
        return C_ADDRESS;
    }

    @Bindable
    public String getC_MAIL() {
        return C_MAIL;
    }

    @Bindable
    public String getC_PHONE() {
        return C_PHONE;
    }

    public void setC_USERNAME(String c_USERNAME) {
        C_USERNAME = c_USERNAME;
        notifyPropertyChanged(BR.c_USERNAME);
    }

    public void setC_PASSWORD(String c_PASSWORD) {
        C_PASSWORD = c_PASSWORD;
        notifyPropertyChanged(BR.c_PASSWORD);
    }

    public void setC_NAME(String c_NAME) {
        C_NAME = c_NAME;
        notifyPropertyChanged(BR.c_NAME);
    }

    public void setC_ADDRESS(String c_ADDRESS) {
        C_ADDRESS = c_ADDRESS;
        notifyPropertyChanged(BR.c_ADDRESS);
    }

    public void setC_MAIL(String c_MAIL) {
        C_MAIL = c_MAIL;
        notifyPropertyChanged(BR.c_MAIL);
    }

    public void setC_PHONE(String c_PHONE) {
        C_PHONE = c_PHONE;
        notifyPropertyChanged(BR.c_PHONE);
    }
}
