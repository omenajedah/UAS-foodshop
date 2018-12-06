package com.firman.ecommerce.foodshop.fragment.shop;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.firman.ecommerce.foodshop.activity.productdetail.ProductDetailActivity;
import com.firman.ecommerce.foodshop.base.BaseRecyclerAdapter;
import com.firman.ecommerce.foodshop.base.BaseViewHolder;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.databinding.ListShopGridBinding;
import com.firman.ecommerce.foodshop.databinding.ListShopLinearBinding;
import com.firman.ecommerce.foodshop.pojo.Product;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Firman on 11/22/2018.
 */
public class ShopAdapter extends BaseRecyclerAdapter<Product> {

    public static final int TYPE_LINEAR = 0;
    public static final int TYPE_GRID = 1;
    private static final String TAG = ShopAdapter.class.getSimpleName();


    private LayoutInflater inflater;
    private int showType;

    public ShopAdapter(List<Product> originalList) {
        super(originalList);
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    @Override
    public boolean onSearch(String filter, Product product) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null)
            inflater = LayoutInflater.from(viewGroup.getContext());

        if (showType == TYPE_LINEAR) {
            ListShopLinearBinding binding = ListShopLinearBinding.inflate(inflater, viewGroup, false);
            return new ShopLinearViewHolder(binding);
        }
        ListShopGridBinding binding = ListShopGridBinding.inflate(inflater, viewGroup, false);
        return new ShopGridViewHolder(binding);
    }

    class ShopGridViewHolder extends BaseViewHolder<ListShopGridBinding> {

        public ShopGridViewHolder(ListShopGridBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListShopViewModel(showList.get(position)));
            binding.executePendingBindings();
        }
    }

    class ShopLinearViewHolder extends BaseViewHolder<ListShopLinearBinding> {

        public ShopLinearViewHolder(ListShopLinearBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            Product product = showList.get(position);
            binding.setVm(new ListShopViewModel(product));
            binding.executePendingBindings();

            if (product.getC_IMAGE_PATH() != null && !product.getC_IMAGE_PATH().equals("null")) {
                AndroidNetworking.forceCancel(TAG + product.getN_ITENO());

                Rx2AndroidNetworking.get(ConstantNetwork.IMAGE)
                        .addQueryParameter("C_IMAGE_PATH", product.getC_IMAGE_PATH())
                        .setTag(TAG + product.getN_ITENO())
                        .addHeaders("Content-Type", "")
                        .build()
                        .getAsBitmap(new BitmapRequestListener() {
                            @Override
                            public void onResponse(Bitmap response) {
                                product.setC_IMAGE(response);
                            }

                            @Override
                            public void onError(ANError anError) {
                                anError.printStackTrace();
                            }
                        });
            }

            binding.rootList.setOnClickListener((v)-> {
                ProductDetailActivity.startThisActivity(binding.getRoot().getContext(),
                        product.getN_ITENO(),
                        product.getV_ITNAM(),
                        product.getSeller().getC_SELLER_NAME()
                );
            });

        }
    }
}
