package com.firman.ecommerce.foodshop.fragment.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.databinding.FragmentRegisterBinding;

/**
 * Created by Firman on 11/18/2018.
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel>
        implements RegisterViewModel.RegisterListener {

    private RegisterViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new RegisterViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
