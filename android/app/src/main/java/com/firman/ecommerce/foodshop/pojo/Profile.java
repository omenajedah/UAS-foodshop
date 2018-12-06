package com.firman.ecommerce.foodshop.pojo;

import android.databinding.Bindable;
import android.graphics.Bitmap;

import com.firman.ecommerce.foodshop.BR;

/**
 * Created by Firman on 11/30/2018.
 */
public class Profile extends Person {

    private Bitmap C_BACKGROUND_IMAGE;
    private Bitmap C_PROFILE_IMAGE;
    private double N_SALDO;

    private String C_BACKGROUND_PATH;
    private String C_PROFILE_PATH;

    @Bindable
    public Bitmap getC_BACKGROUND_IMAGE() {
        return C_BACKGROUND_IMAGE;
    }

    @Bindable
    public Bitmap getC_PROFILE_IMAGE() {
        return C_PROFILE_IMAGE;
    }

    @Bindable
    public double getN_SALDO() {
        return N_SALDO;
    }

    @Bindable
    public String getC_BACKGROUND_PATH() {
        return C_BACKGROUND_PATH;
    }

    @Bindable
    public String getC_PROFILE_PATH() {
        return C_PROFILE_PATH;
    }

    public void setC_BACKGROUND_IMAGE(Bitmap c_BACKGROUND_IMAGE) {
        C_BACKGROUND_IMAGE = c_BACKGROUND_IMAGE;
        notifyPropertyChanged(BR.c_BACKGROUND_IMAGE);
    }

    public void setC_PROFILE_IMAGE(Bitmap c_PROFILE_IMAGE) {
        C_PROFILE_IMAGE = c_PROFILE_IMAGE;
        notifyPropertyChanged(BR.c_PROFILE_IMAGE);
    }

    public void setN_SALDO(double n_SALDO) {
        N_SALDO = n_SALDO;
        notifyPropertyChanged(BR.n_SALDO);
    }

    public void setC_BACKGROUND_PATH(String c_BACKGROUND_PATH) {
        C_BACKGROUND_PATH = c_BACKGROUND_PATH;
        notifyPropertyChanged(BR.c_BACKGROUND_PATH);
    }

    public void setC_PROFILE_PATH(String c_PROFILE_PATH) {
        C_PROFILE_PATH = c_PROFILE_PATH;
        notifyPropertyChanged(BR.c_PROFILE_PATH);
    }
}
