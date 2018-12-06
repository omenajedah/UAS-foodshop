package com.firman.ecommerce.foodshop.pojo;

import android.databinding.Bindable;

import com.firman.ecommerce.foodshop.BR;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Firman on 12/1/2018.
 */
public class ProductCart extends Product {

    private long N_BOOK;
    private String V_NOTES;

    @Bindable
    public long getN_BOOK() {
        return N_BOOK;
    }

    @Bindable
    public String getV_NOTES() {
        return V_NOTES;
    }

    public void setN_BOOK(long n_BOOK) {
        N_BOOK = n_BOOK;
        notifyPropertyChanged(BR.n_BOOK);
    }

    public void setV_NOTES(String v_NOTES) {
        V_NOTES = v_NOTES;
        notifyPropertyChanged(BR.v_NOTES);
    }

    public static ProductCart create(String n_ITENO, String v_ITNAM, String v_SELLER_NAME) {
        ProductCart productCart = new ProductCart();
        productCart.setN_ITENO(n_ITENO);
        productCart.setV_ITNAM(v_ITNAM);
        productCart.setSeller(new Seller());
        productCart.setCategory(new Category());
        productCart.getSeller().setC_SELLER_NAME(v_SELLER_NAME);
        return productCart;
    }

    public static ProductCart fromJson(JSONObject jsonObject) throws JSONException {
        ProductCart productCart = new ProductCart();
        productCart.setSeller(new Seller());
        productCart.setCategory(new Category());

        productCart.setN_ITENO(jsonObject.getString("N_ITENO"));
        productCart.setV_ITNAM(jsonObject.getString("V_ITNAM"));
        productCart.setV_SHORTDESC(jsonObject.optString("V_SHORTDESC"));
        productCart.setV_DESC(jsonObject.optString("V_DESC"));
        productCart.setN_PRICE(jsonObject.optLong("N_PRICE"));
        productCart.setN_QOH(jsonObject.optLong("N_QOH"));
        productCart.setN_BOOK(jsonObject.optLong("N_BOOK"));
        productCart.setC_IMAGE_PATH(jsonObject.optString("C_IMAGE_PATH"));
        productCart.getSeller().setC_SELLER_ID(jsonObject.optString("C_SELLER_ID"));
        productCart.getSeller().setC_SELLER_NAME(jsonObject.optString("C_SELLER_NAME"));
        productCart.getSeller().setC_SELLER_ALIAS(jsonObject.optString("C_SELLER_ALIAS"));
        productCart.getSeller().setC_SELLER_ADDR(jsonObject.optString("C_SELLER_ADDR"));
        productCart.getSeller().setC_SELLER_MAIL(jsonObject.optString("C_SELLER_MAIL"));
        productCart.getSeller().setC_SELLER_PHONE(jsonObject.optString("C_SELLER_PHONE"));
        productCart.getSeller().setC_SELLER_BACKGROUND_IMAGE(jsonObject.optString("C_SELLER_BACKGROUND_IMAGE"));
        productCart.getSeller().setC_SELLER_PROFILE_IMAGE(jsonObject.optString("C_SELLER_PROFILE_IMAGE"));
        productCart.getSeller().setC_SELLER_STATUS(jsonObject.optInt("C_SELLER_STATUS") == 1);

        productCart.getCategory().setC_CATEGORY_ID(jsonObject.optString("C_CATEGORY_ID"));
        productCart.getCategory().setC_CATEGORY_NAME(jsonObject.optString("C_CATEGORY_NAME"));

        return productCart;
    }

    @Override
    public void assignJson(JSONObject jsonObject) throws JSONException {
        super.assignJson(jsonObject);
        setN_BOOK(jsonObject.optLong("N_BOOK"));
        setV_NOTES(jsonObject.optString("V_NOTES"));
    }
}
