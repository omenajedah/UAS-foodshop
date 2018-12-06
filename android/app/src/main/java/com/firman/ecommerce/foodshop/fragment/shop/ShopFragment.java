package com.firman.ecommerce.foodshop.fragment.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.databinding.FragmentShopBinding;
import com.firman.ecommerce.foodshop.pojo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 11/17/2018.
 */
public class ShopFragment extends BaseFragment<FragmentShopBinding, ShopViewModel>
        implements ShopViewModel.ShopListener {

    private ShopViewModel viewModel;
    private ShopAdapter shopAdapter;
    private List<Product> showList = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public ShopViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new ShopViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.setHasFixedSize(true);
        getBinding().rootListData.setOnRefreshListener(viewModel);
        shopAdapter = new ShopAdapter(showList);
        getBinding().listData.setAdapter(shopAdapter);

        viewModel.refresh();
    }

    @Override
    public void onProductAdded(List<Product> products) {
        getBinding().rootListData.setRefreshing(false);
        showList.clear();
        showList.addAll(products);
    }

    @Override
    public void onLoad() {
        getBinding().rootListData.setRefreshing(true);

    }

    @Override
    public void onAddedCompleted() {
        shopAdapter.updateDataSet();
    }

    @Override
    public void onError(Throwable cause) {
        Toast.makeText(getContext(), "Error Happen", Toast.LENGTH_SHORT).show();
    }
}
