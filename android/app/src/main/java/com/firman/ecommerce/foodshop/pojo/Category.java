package com.firman.ecommerce.foodshop.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.firman.ecommerce.foodshop.BR;

/**
 * Created by Firman on 11/22/2018.
 */
public class Category extends BaseObservable {

    private String C_CATEGORY_ID;
    private String C_CATEGORY_NAME;

    @Bindable
    public String getC_CATEGORY_ID() {
        return C_CATEGORY_ID;
    }

    @Bindable
    public String getC_CATEGORY_NAME() {
        return C_CATEGORY_NAME;
    }

    public void setC_CATEGORY_ID(String c_CATEGORY_ID) {
        C_CATEGORY_ID = c_CATEGORY_ID;
        notifyPropertyChanged(BR.c_CATEGORY_ID);
    }

    public void setC_CATEGORY_NAME(String c_CATEGORY_NAME) {
        C_CATEGORY_NAME = c_CATEGORY_NAME;
        notifyPropertyChanged(BR.c_CATEGORY_NAME);
    }
}
