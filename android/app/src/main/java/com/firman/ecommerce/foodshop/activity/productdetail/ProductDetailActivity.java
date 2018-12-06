package com.firman.ecommerce.foodshop.activity.productdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseActivity;
import com.firman.ecommerce.foodshop.databinding.ActivityProductDetailBinding;
import com.firman.ecommerce.foodshop.pojo.Product;

/**
 * Created by Firman on 12/1/2018.
 */
public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel>
        implements ProductDetailViewModel.ProductDetailListener {

    public static void startThisActivity(Context context, String n_ITENO, String v_ITNAM, String v_SELLER_NAME) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("N_ITENO", n_ITENO);
        intent.putExtra("V_ITNAM", v_ITNAM);
        intent.putExtra("V_SELLER_NAME", v_SELLER_NAME);
        context.startActivity(intent);
    }

    private ProductDetailViewModel viewModel;

    @Override
    protected int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected ProductDetailViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new ProductDetailViewModel(this, this);

        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        Log.d(TAG, "Called");
        if (!getIntent().hasExtra("N_ITENO"))
            finish();

        viewModel.setProduct(Product.create(getIntent().getStringExtra("N_ITENO"),
                getIntent().getStringExtra("V_ITNAM"),
                getIntent().getStringExtra("V_SELLER_NAME")));
        viewModel.refresh();
    }
}
