package com.firman.ecommerce.foodshop.fragment.profile;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.view.LayoutInflater;

import com.firman.ecommerce.foodshop.activity.login.LoginRegisterActivity;
import com.firman.ecommerce.foodshop.base.BaseActivity;
import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.SessionHandler;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.databinding.DialogTopupBinding;
import com.firman.ecommerce.foodshop.pojo.Profile;
import com.firman.ecommerce.foodshop.widget.CustomDialog;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/17/2018.
 */
public class ProfileViewModel extends BaseViewModel implements DialogTopupViewModel.DialogTopupListener {

    private final ProfileListener profileListener;
    private final Profile profile;
    private ObservableBoolean editMode = new ObservableBoolean();
    private CustomDialog dialog;


    public ProfileViewModel(Context context, ProfileListener profileListener) {
        super(context);
        this.profileListener = profileListener;
        this.profile = new Profile();
        initProfile();

    }

    private void initProfile() {
        SessionHandler.LoginProfile loginProfile = getGeneralSession().getProfile();
        profile.setN_SALDO(loginProfile.getN_SALDO());
        profile.setC_USERNAME(loginProfile.getC_USERNAME());
        profile.setC_NAME(loginProfile.getC_NAME());
        profile.setC_MAIL(loginProfile.getC_MAIL());
        profile.setC_PHONE(loginProfile.getC_PHONE());
    }

    public Profile getProfile() {
        return profile;
    }

    public ObservableBoolean getEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }

    public void onTopupClick() {
        DialogTopupBinding binding = DialogTopupBinding.inflate(LayoutInflater.from(getContext()), null);
        binding.setVm(new DialogTopupViewModel(this));
        dialog = CustomDialog.get(getContext())
                .addView(binding.getRoot())
                .title("Topup")
                .cancelable(true);
        dialog.show();
    }

    public void onRefreshClick() {
        refresh();
    }

    public void refresh() {
        getCompositeDisposable().add(
                getProfileFromApi().subscribe(profile1 -> {

                }, throwable -> {

                })
        );
    }

    private Single<Profile> getProfileFromApi() {
        return Rx2AndroidNetworking.get(ConstantNetwork.PROFILE)
                .build()
                .getJSONObjectSingle()
                .map(jsonObject -> {
                    JSONObject profil = jsonObject.getJSONArray("DataRow").getJSONObject(0);
                    profile.setN_SALDO(profil.optDouble("N_SALDO"));
                    profile.setC_PHONE(profil.optString("C_PHONE"));
                    profile.setC_MAIL(profil.optString("C_MAIL"));
                    profile.setC_ADDRESS(profil.optString("C_ADDRESS"));
                    profile.setC_NAME(profil.optString("C_NAME"));
                    profile.setC_BACKGROUND_PATH(profil.optString("C_BACKGROUND_IMAGE"));
                    profile.setC_PROFILE_PATH(profil.optString("C_PROFILE_IMAGE"));
                    getGeneralSession().put(SessionHandler.KEY_NSALDO, (long) profile.getN_SALDO());

                    return profile;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public void onLogout() {
        getGeneralSession().logout();
        ((BaseActivity) getContext()).finish();
        LoginRegisterActivity.startThisActivity(getContext());
    }

    @Override
    public void onTopupSuccess() {
        if (dialog != null)
            dialog.dismiss();
        CustomDialog.get(getContext())
                .title("Topup")
                .message("Topup Success!")
                .addPositiveButton("OK", (dialog1, which) -> refresh())
                .show();
    }

    @Override
    public void onTopupFailed(Throwable cause) {
        CustomDialog.get(getContext())
                .title("Topup")
                .message("Topup Failed, Please Try Again Later.")
                .addPositiveButton("OK", null)
                .show();
    }

    public interface ProfileListener {

    }
}
