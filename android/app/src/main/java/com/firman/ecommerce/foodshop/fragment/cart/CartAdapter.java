package com.firman.ecommerce.foodshop.fragment.cart;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.firman.ecommerce.foodshop.base.BaseRecyclerAdapter;
import com.firman.ecommerce.foodshop.base.BaseViewHolder;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.common.util.Utils;
import com.firman.ecommerce.foodshop.databinding.ListCartSellerBinding;
import com.firman.ecommerce.foodshop.pojo.ProductCart;
import com.firman.ecommerce.foodshop.databinding.ListCartProductBinding;
import com.firman.ecommerce.foodshop.pojo.Seller;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

/**
 * Created by Firman on 12/1/2018.
 */
public class CartAdapter extends BaseRecyclerAdapter<Object> {

    private final int TYPE_SELLER = 0;
    private final int TYPE_PRODUCT = 1;

    private OnDataChangedListener changedListener;
    List<Object> originalList;


    public CartAdapter(List<Object> originalList, OnDataChangedListener changedListener) {
        super(originalList);
        this.originalList = originalList;
        this.changedListener=changedListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (showList.get(position) instanceof String)
            return TYPE_SELLER;
        return TYPE_PRODUCT;
    }

    @Override
    public boolean onSearch(String filter, Object o) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_PRODUCT) {
            ListCartProductBinding binding = ListCartProductBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProductCartViewHolder(binding);
        }
        ListCartSellerBinding binding = ListCartSellerBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SellerCartViewHolder(binding);
    }

    class ProductCartViewHolder extends BaseViewHolder<ListCartProductBinding>
            implements ListProductCartViewModel.OnDeleteListener {

        public ProductCartViewHolder(ListCartProductBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            ProductCart product = (ProductCart) showList.get(position);
            ListProductCartViewModel viewModel =
                    new ListProductCartViewModel(binding.getRoot().getContext(),
                            product, position, this);
            binding.setVm(viewModel);
            binding.executePendingBindings();

            if (product.getC_IMAGE_PATH() != null && !product.getC_IMAGE_PATH().equals("null")) {
                AndroidNetworking.forceCancel(product.getN_ITENO());

                Rx2AndroidNetworking.get(ConstantNetwork.IMAGE)
                        .addQueryParameter("C_IMAGE_PATH", product.getC_IMAGE_PATH())
                        .setTag(product.getN_ITENO())
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
        }

        @Override
        public void onChange(int pos) {
            if (changedListener != null)
                changedListener.onDataChanged();
        }

        @Override
        public void onDelete(int pos) {
            originalList.remove(pos);
            updateDataSet();
            if (changedListener != null)
                changedListener.onDataRemoved();
        }
    }

    class SellerCartViewHolder extends BaseViewHolder<ListCartSellerBinding> {

        public SellerCartViewHolder(ListCartSellerBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.vITNAM.getLayoutParams();
            params.setMarginStart(8);
            params.setMarginEnd(8);

            if (position == 0)
                params.setMargins(8, 0, 8, 0);
            else
                params.setMargins(8, 16, 8, 0);
            binding.vITNAM.setLayoutParams(params);
            binding.setVSELLER((String) showList.get(position));
            binding.executePendingBindings();
        }
    }

    public interface OnDataChangedListener {
        void onDataChanged();
        void onDataRemoved();
    }
}
