package com.firman.ecommerce.foodshop.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.activity.home.HomeActivity;
import com.firman.ecommerce.foodshop.base.BaseActivity;
import com.firman.ecommerce.foodshop.databinding.ActivityLoginRegisterBinding;
import com.firman.ecommerce.foodshop.fragment.login.LoginFragment;
import com.firman.ecommerce.foodshop.fragment.register.RegisterFragment;

public class LoginRegisterActivity extends BaseActivity<ActivityLoginRegisterBinding, LoginRegisterViewModel> {

    private LoginRegisterViewModel viewModel;

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, LoginRegisterActivity.class));
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    public LoginRegisterViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new LoginRegisterViewModel(this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (viewModel.getGeneralSession().isLogin()) {
            HomeActivity.startThisActivity(this);
            finish();
        }
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        getBinding().pager.setAdapter(adapter);

        adapter.add("Login", new LoginFragment());
        adapter.add("Register", new RegisterFragment());

        getBinding().tabs.setupWithViewPager(getBinding().pager);
        getBinding().tabs.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void showSnackbar(String error) {
        Snackbar.make(getBinding().tabs, error, Toast.LENGTH_SHORT).show();
    }
}
