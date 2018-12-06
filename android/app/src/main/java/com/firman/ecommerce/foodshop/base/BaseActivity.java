package com.firman.ecommerce.foodshop.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Firman on 11/17/2018.
 */
public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();


    private T binding;

    protected abstract int getBindingVariable();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract V getViewModel();

    public T getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();

    }


    private void performDataBinding() {
        if (getLayoutId() == -1)
            return;
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        if (getViewModel() == null || getBindingVariable() == -1)
            return;

        binding.setVariable(getBindingVariable(), getViewModel());
        binding.executePendingBindings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getViewModel() != null)
            getViewModel().onCleared();
    }
}