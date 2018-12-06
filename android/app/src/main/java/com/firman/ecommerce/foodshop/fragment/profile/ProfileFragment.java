package com.firman.ecommerce.foodshop.fragment.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firman.ecommerce.foodshop.BR;
import com.firman.ecommerce.foodshop.R;
import com.firman.ecommerce.foodshop.base.BaseFragment;
import com.firman.ecommerce.foodshop.databinding.FragmentProfileBinding;

/**
 * Created by Firman on 11/17/2018.
 */
public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel>
        implements ProfileViewModel.ProfileListener {

    private ProfileViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        if (viewModel == null)
            viewModel = new ProfileViewModel(getContext(), this);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewModel.getEditMode().get())
            inflater.inflate(R.menu.profile_save_menu, menu);
        else
            inflater.inflate(R.menu.profile_change_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_profile:
                viewModel.setEditMode(true);
                break;
            case R.id.save_profile:
                viewModel.setEditMode(false);
                break;
        }
        getActivity().invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.setEditMode(false);
    }
}
