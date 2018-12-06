package com.firman.ecommerce.foodshop.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Firman on 11/17/2018.
 */
public abstract class BaseViewHolder<E extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected final E binding;

    public BaseViewHolder(E binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public abstract void onBind(int position);

}
