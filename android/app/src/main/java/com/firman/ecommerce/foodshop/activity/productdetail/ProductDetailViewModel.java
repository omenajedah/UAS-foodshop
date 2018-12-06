package com.firman.ecommerce.foodshop.activity.productdetail;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.pojo.Product;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 12/1/2018.
 */
public class ProductDetailViewModel extends BaseViewModel {

    private final ProductDetailListener listener;
    private final Product product;

    public ProductDetailViewModel(Context context, ProductDetailListener listener) {
        super(context);
        this.listener = listener;
        product = new Product();
        //ToDo implements this
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.setN_ITENO(product.getN_ITENO());
        this.product.setV_ITNAM(product.getV_ITNAM());
        this.product.setCategory(product.getCategory());
        this.product.setSeller(product.getSeller());
    }

    public void onImageClick() {

    }

    public void onCartClick() {


    }

    public void onAddCartClick() {
        AndroidNetworking.forceCancel(TAG);
        Rx2AndroidNetworking.post(ConstantNetwork.CART)
                .addBodyParameter("C_USERNAME", getGeneralSession().getProfile().getC_USERNAME())
                .addBodyParameter("N_ITENO", product.getN_ITENO())
                .addBodyParameter("N_BOOK", String.valueOf(1))
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            product.assignJson(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void onSellerClick() {

    }

    public void refresh() {
        getCompositeDisposable().clear();

        getCompositeDisposable().add(getSingleProduct().subscribe(product1 -> {
            downloadImage();
        }, throwable -> {
            throwable.printStackTrace();
            Log.e(TAG, "Failed load data");
        }));

    }

    private void downloadImage() {
        getCompositeDisposable().add(Rx2AndroidNetworking.get(ConstantNetwork.IMAGE)
                .addQueryParameter("C_IMAGE_PATH", product.getC_IMAGE_PATH())
                .setTag(product.getN_ITENO())
                .build()
                .getBitmapSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product::setC_IMAGE, throwable -> {
                    throwable.printStackTrace();
                    Log.e(TAG, "Failed load image");
                }));

        if (product.getSeller().getC_SELLER_PROFILE_IMAGE() == null ||
                product.getSeller().getC_SELLER_PROFILE_IMAGE().equals("null")) {
            getCompositeDisposable().add(Rx2AndroidNetworking.get(ConstantNetwork.IMAGE)
                    .addQueryParameter("C_IMAGE_PATH", product.getSeller().getC_SELLER_PROFILE_IMAGE())
                    .setTag(product.getN_ITENO())
                    .build()
                    .getBitmapSingle()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> product.getSeller().setC_PROFILE_IMAGE(bitmap), throwable -> {
                        throwable.printStackTrace();
                        Log.e(TAG, "Failed load image");
                    }));
        }

    }

    private Single<Product> getSingleProduct() {
        return Rx2AndroidNetworking.get(ConstantNetwork.PRODUCT)
                .addQueryParameter("N_ITENO", product.getN_ITENO())
                .build()
                .getJSONObjectSingle()
                .map(jsonObject -> {
                    if (jsonObject.getInt("RowCount") > 0)
                        product.assignJson(jsonObject.getJSONArray("DataRow").getJSONObject(0));
                    return product;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface ProductDetailListener {

    }
}
