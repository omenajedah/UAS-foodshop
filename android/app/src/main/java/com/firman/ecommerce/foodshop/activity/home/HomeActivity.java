package com.firman.ecommerce.foodshop.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.activity.login.TabFragmentPagerAdapter;
import com.firman.ecommerce.foodshop.base.BaseActivity;
import com.firman.ecommerce.foodshop.databinding.ActivityHomeBinding;
import com.firman.ecommerce.foodshop.fragment.cart.CartFragment;
import com.firman.ecommerce.foodshop.fragment.history.HistoryFragment;
import com.firman.ecommerce.foodshop.fragment.profile.ProfileFragment;
import com.firman.ecommerce.foodshop.fragment.shop.ShopFragment;

/**
 * Created by Firman on 11/17/2018.
 */
public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel>
        implements HomeViewModel.HomeListener {

    private HomeViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new HomeViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        viewModel.login();
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        getBinding().pager.setAdapter(adapter);

        adapter.add("Shop", new ShopFragment());
        adapter.add("Cart", new CartFragment());
        adapter.add("History", new HistoryFragment());
        adapter.add("Profile", new ProfileFragment());

        getBinding().tabs.setupWithViewPager(getBinding().pager);
        getBinding().tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        getBinding().pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTitle(adapter.getPageTitle(position));
            }

            @Override
            public void onPageSelected(int position) {
                setTitle(adapter.getPageTitle(position));

                adapter.getItem(position).onViewCreated(null, null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public static void startThisActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }
}
