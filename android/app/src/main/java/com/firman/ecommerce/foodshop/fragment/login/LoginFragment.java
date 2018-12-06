package com.firman.ecommerce.foodshop.fragment.login;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.activity.home.HomeActivity;
import com.firman.ecommerce.foodshop.activity.login.LoginRegisterActivity;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.databinding.FragmentLoginBinding;

/**
 * Created by Firman on 11/18/2018.
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel>
        implements LoginViewModel.LoginListener {

    private LoginViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LoginViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onSuccess() {
        HomeActivity.startThisActivity(getContext());
    }

    @Override
    public void onError(String cause) {
        ((LoginRegisterActivity)getContext()).showSnackbar(cause);
    }
}
