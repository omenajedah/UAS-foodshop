package com.firman.ecommerce.foodshop.fragment.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.Toast;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.databinding.FragmentHistoryBinding;
import com.firman.ecommerce.foodshop.pojo.History;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 11/17/2018.
 */
public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, HistoryViewModel>
        implements HistoryViewModel.HistoryListener {

    private HistoryViewModel viewModel;
    private List<History> histories = new ArrayList<>();
    private HistoryAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public HistoryViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new HistoryViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HistoryAdapter(histories);
        getBinding().listData.setAdapter(adapter);
        getBinding().listData.setItemAnimator(new DefaultItemAnimator());
        getBinding().listData.setHasFixedSize(true);
        getBinding().listData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getBinding().rootListData.setOnRefreshListener(viewModel);
        viewModel.refresh();
    }

    @Override
    public void onRefresh(List<History> histories) {
        getBinding().rootListData.setRefreshing(false);
        this.histories.clear();
        this.histories.addAll(histories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoad() {
        getBinding().rootListData.setRefreshing(true);

    }

    @Override
    public void onError(Throwable cause) {
        Toast.makeText(getContext(), "Error happen.", Toast.LENGTH_SHORT).show();
    }
}
