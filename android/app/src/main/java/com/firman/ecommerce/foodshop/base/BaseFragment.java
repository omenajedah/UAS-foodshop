package com.firman.ecommerce.foodshop.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Firman on 11/18/2018.
 */
public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>
        extends Fragment {

    protected final String TAG = getClass().getSimpleName();

    private T binding;

    protected abstract int getBindingVariable();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract V getViewModel();

    protected T getBinding() {
        return binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        performDataBinding(inflater, container);
        return binding.getRoot();
    }

    private void performDataBinding(LayoutInflater inflater, ViewGroup container) {
        if (getLayoutId() == -1)
            return;
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        if (getViewModel() == null || getBindingVariable() == -1)
            return;

        binding.setVariable(getBindingVariable(), getViewModel());
        binding.executePendingBindings();
    }

    public void setTitle(@StringRes int res) {
        setTitle(getString(res));
    }

    public void setTitle(String title) {
        if (getActivity() != null)
            getActivity().setTitle(title);
    }

    public void setSubTitle(@StringRes int res) {
        if (getActivity() != null)
            setSubTitle(getString(res));
    }
    public void setSubTitle(String subTitle) {
        if (getActivity() != null && getActivity().getActionBar() != null)
            getActivity().getActionBar().setSubtitle(subTitle);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getViewModel() != null)
            getViewModel().onCleared();
    }
}
