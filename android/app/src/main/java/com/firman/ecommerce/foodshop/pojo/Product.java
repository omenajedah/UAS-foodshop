package com.firman.ecommerce.foodshop.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.common.reflect.CustomSetter;
import com.firman.ecommerce.foodshop.common.reflect.annotation.CustomSetterAnnotation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Firman on 11/22/2018.
 */
public class Product extends BaseObservable {

    private String N_ITENO;
    private String V_ITNAM;
    private String V_SHORTDESC;
    private String V_DESC;
    private long N_PRICE;
    private long N_QOH;

    @CustomSetterAnnotation(setterClass = CustomSetter.class, methodName = "setSeller")
    private Seller seller;

    @CustomSetterAnnotation(setterClass = CustomSetter.class, methodName = "setCategory")
    private Category category;

    private Bitmap C_IMAGE;
    private String C_IMAGE_PATH;

    public static Product create(String n_ITENO, String v_ITNAM, String v_SELLER_NAME) {
        Product product = new Product();
        product.setN_ITENO(n_ITENO);
        product.setV_ITNAM(v_ITNAM);
        product.setSeller(new Seller());
        product.setCategory(new Category());
        product.getSeller().setC_SELLER_NAME(v_SELLER_NAME);
        return product;
    }

    public static Product fromJson(JSONObject jsonObject) throws JSONException {
        Product product = new Product();
        product.setSeller(new Seller());
        product.setCategory(new Category());

        product.setN_ITENO(jsonObject.getString("N_ITENO"));
        product.setV_ITNAM(jsonObject.getString("V_ITNAM"));
        product.setV_SHORTDESC(jsonObject.optString("V_SHORTDESC"));
        product.setV_DESC(jsonObject.optString("V_DESC"));
        product.setN_PRICE(jsonObject.optLong("N_PRICE"));
        product.setN_QOH(jsonObject.optLong("N_QOH"));
        product.setC_IMAGE_PATH(jsonObject.optString("C_IMAGE_PATH"));
        product.getSeller().setC_SELLER_ID(jsonObject.optString("C_SELLER_ID"));
        product.getSeller().setC_SELLER_NAME(jsonObject.optString("C_SELLER_NAME"));
        product.getSeller().setC_SELLER_ALIAS(jsonObject.optString("C_SELLER_ALIAS"));
        product.getSeller().setC_SELLER_ADDR(jsonObject.optString("C_SELLER_ADDR"));
        product.getSeller().setC_SELLER_MAIL(jsonObject.optString("C_SELLER_MAIL"));
        product.getSeller().setC_SELLER_PHONE(jsonObject.optString("C_SELLER_PHONE"));
        product.getSeller().setC_SELLER_BACKGROUND_IMAGE(jsonObject.optString("C_SELLER_BACKGROUND_IMAGE"));
        product.getSeller().setC_SELLER_PROFILE_IMAGE(jsonObject.optString("C_SELLER_PROFILE_IMAGE"));
        product.getSeller().setC_SELLER_STATUS(jsonObject.optInt("C_SELLER_STATUS") == 1);

        product.getCategory().setC_CATEGORY_ID(jsonObject.optString("C_CATEGORY_ID"));
        product.getCategory().setC_CATEGORY_NAME(jsonObject.optString("C_CATEGORY_NAME"));

        return product;
    }

    public void assignJson(JSONObject jsonObject) throws JSONException {
        setSeller(new Seller());
        setCategory(new Category());

        setN_ITENO(jsonObject.getString("N_ITENO"));
        setV_ITNAM(jsonObject.getString("V_ITNAM"));
        setV_SHORTDESC(jsonObject.optString("V_SHORTDESC"));
        setV_DESC(jsonObject.optString("V_DESC"));
        setN_PRICE(jsonObject.optLong("N_PRICE"));
        setN_QOH(jsonObject.optLong("N_QOH"));
        setC_IMAGE_PATH(jsonObject.optString("C_IMAGE_PATH"));

        getSeller().setC_SELLER_ID(jsonObject.optString("C_SELLER_ID"));
        getSeller().setC_SELLER_NAME(jsonObject.optString("C_SELLER_NAME"));
        getSeller().setC_SELLER_ALIAS(jsonObject.optString("C_SELLER_ALIAS"));
        getSeller().setC_SELLER_ADDR(jsonObject.optString("C_SELLER_ADDR"));
        getSeller().setC_SELLER_MAIL(jsonObject.optString("C_SELLER_MAIL"));
        getSeller().setC_SELLER_PHONE(jsonObject.optString("C_SELLER_PHONE"));
        getSeller().setC_SELLER_BACKGROUND_IMAGE(jsonObject.optString("C_SELLER_BACKGROUND_IMAGE"));
        getSeller().setC_SELLER_PROFILE_IMAGE(jsonObject.optString("C_SELLER_PROFILE_IMAGE"));
        getSeller().setC_SELLER_STATUS(jsonObject.optInt("C_SELLER_STATUS") == 1);

        getCategory().setC_CATEGORY_ID(jsonObject.optString("C_CATEGORY_ID"));
        getCategory().setC_CATEGORY_NAME(jsonObject.optString("C_CATEGORY_NAME"));
    }

    @Bindable
    public String getN_ITENO() {
        return N_ITENO;
    }

    @Bindable
    public String getV_ITNAM() {
        return V_ITNAM;
    }

    @Bindable
    public String getV_SHORTDESC() {
        return V_SHORTDESC;
    }

    @Bindable
    public String getV_DESC() {
        return V_DESC;
    }

    @Bindable
    public long getN_PRICE() {
        return N_PRICE;
    }

    @Bindable
    public long getN_QOH() {
        return N_QOH;
    }

    @Bindable
    public Seller getSeller() {
        return seller;
    }

    @Bindable
    public Category getCategory() {
        return category;
    }

    @Bindable
    public Bitmap getC_IMAGE() {
        return C_IMAGE;
    }

    @Bindable
    public String getC_IMAGE_PATH() {
        return C_IMAGE_PATH;
    }

    public void setN_ITENO(String n_ITENO) {
        N_ITENO = n_ITENO;
        notifyPropertyChanged(BR.n_ITENO);
    }

    public void setV_ITNAM(String v_ITNAM) {
        V_ITNAM = v_ITNAM;
        notifyPropertyChanged(BR.v_ITNAM);
    }

    public void setV_SHORTDESC(String v_SHORTDESC) {
        V_SHORTDESC = v_SHORTDESC;
        notifyPropertyChanged(BR.v_SHORTDESC);
    }

    public void setV_DESC(String v_DESC) {
        V_DESC = v_DESC;
        notifyPropertyChanged(BR.v_DESC);
    }

    public void setN_PRICE(long n_PRICE) {
        N_PRICE = n_PRICE;
        notifyPropertyChanged(BR.n_PRICE);
    }

    public void setN_QOH(long n_QOH) {
        N_QOH = n_QOH;
        notifyPropertyChanged(BR.n_QOH);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
        notifyPropertyChanged(BR.seller);
    }

    public void setCategory(Category category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    public void setC_IMAGE(Bitmap c_IMAGE) {
        C_IMAGE = c_IMAGE;
        notifyPropertyChanged(BR.c_IMAGE);
    }

    public void setC_IMAGE_PATH(String c_IMAGE_PATH) {
        C_IMAGE_PATH = c_IMAGE_PATH;
        notifyPropertyChanged(BR.c_IMAGE_PATH);
    }
}
