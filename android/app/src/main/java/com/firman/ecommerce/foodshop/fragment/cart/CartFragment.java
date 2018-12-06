package com.firman.ecommerce.foodshop.fragment.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.common.util.Utils;
import com.firman.ecommerce.foodshop.databinding.FragmentCartBinding;
import com.firman.ecommerce.foodshop.pojo.ProductCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 11/17/2018.
 */
public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel>
        implements CartViewModel.CartListener, CartAdapter.OnDataChangedListener {

    private CartViewModel viewModel;
    private List<Object> objects = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public CartViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new CartViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CartAdapter(objects, this);
        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.setHasFixedSize(true);
        getBinding().rootListData.setOnRefreshListener(viewModel);

        viewModel.refresh();
    }

    @Override
    public void onLoad() {
        getBinding().rootListData.setRefreshing(true);

    }

    @Override
    public void onRefresh(List<Object> objects) {
        getBinding().rootListData.setRefreshing(false);
        this.objects.clear();
        this.objects.addAll(objects);
        adapter.updateDataSet();
        onDataChanged();
    }

    @Override
    public void onError(Throwable cause) {

    }

    @Override
    public void onDataChanged() {
        int totalItems = 0;
        long totalPrice = 0;
        for (int i=0;i<objects.size();i++) {
            Object object = objects.get(i);
            if (object instanceof ProductCart) {
                totalItems++;
                totalPrice+=(((ProductCart) object).getN_PRICE() * ((ProductCart) object).getN_BOOK());
            }
        }

        viewModel.setTotalItems(totalItems);
        viewModel.setTotalPrice(totalPrice);

        getBinding().totalItems.setText(String.valueOf(totalItems));
        getBinding().totalPrice.setText(Utils.formatIDR(totalPrice));
    }

    @Override
    public void onDataRemoved() {
        viewModel.refresh();
    }
}
