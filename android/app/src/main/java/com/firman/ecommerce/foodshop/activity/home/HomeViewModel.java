package com.firman.ecommerce.foodshop.activity.home;

import android.content.Context;
import android.view.View;

import com.firman.ecommerce.foodshop.base.BaseActivity;
import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.common.util.Utils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/17/2018.
 */
public class HomeViewModel extends BaseViewModel {

    private final HomeListener homeListener;

    public HomeViewModel(Context context, HomeListener homeListener) {
        super(context);
        this.homeListener = homeListener;
    }

    public void login() {
        getCompositeDisposable().add(doLogin().subscribe(jsonObject -> {

            if (jsonObject.getBoolean("success")) {
                jsonObject = jsonObject.getJSONObject("profile");
                getGeneralSession().login(getGeneralSession().getProfile().getC_USERNAME(),
                        getGeneralSession().getProfile().getC_PASSWORD(),
                        jsonObject.getString("C_NAME"),
                        jsonObject.getString("C_ADDRESS"),
                        jsonObject.getString("C_MAIL"),
                        jsonObject.getString("C_PHONE"),
                        jsonObject.getLong("N_SALDO")
                );
            } else {
                getGeneralSession().logout();
                ((BaseActivity)getContext()).finish();
            }
        }, throwable -> {
            throwable.printStackTrace();
        }));


    }

    private Observable<JSONObject> doLogin() {

        Map<String, String> param = new HashMap<>();
        param.put("C_USERNAME", getGeneralSession().getProfile().getC_USERNAME());
        param.put("C_PASSWORD", getGeneralSession().getProfile().getC_PASSWORD());
        return Rx2AndroidNetworking.post(ConstantNetwork.LOGIN)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface HomeListener {

    }
}
