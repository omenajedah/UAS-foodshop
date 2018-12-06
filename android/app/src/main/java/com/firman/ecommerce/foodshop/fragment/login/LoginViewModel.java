package com.firman.ecommerce.foodshop.fragment.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.common.util.Utils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/18/2018.
 */
public class LoginViewModel extends BaseViewModel {

    private ObservableField<String> username = new ObservableField<>("");
    private ObservableField<String> password = new ObservableField<>("");

    private Disposable loginDisposable;

    private final LoginListener loginListener;

    private final ProgressDialog dialog;

    public LoginViewModel(Context context, LoginListener loginListener) {
        super(context);
        this.loginListener = loginListener;
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait");
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public void login(View v) {
        Utils.hideKeyboard(v);
        if (loginDisposable != null) {
            loginDisposable.dispose();
            loginDisposable = null;
        }
        loginDisposable = doLogin().subscribe(jsonObject -> {
            dialog.dismiss();

            if (jsonObject.getBoolean("success")) {
                jsonObject = jsonObject.getJSONObject("profile");
                getGeneralSession().login(username.get(),
                        password.get(),
                        jsonObject.getString("C_NAME"),
                        jsonObject.getString("C_ADDRESS"),
                        jsonObject.getString("C_MAIL"),
                        jsonObject.getString("C_PHONE"),
                        jsonObject.getLong("N_SALDO")
                        );
                if (loginListener != null)
                    loginListener.onSuccess();
            } else
                loginListener.onError(jsonObject.getString("reason"));
        }, throwable -> {
            dialog.dismiss();
            if (loginListener != null)
                loginListener.onError(throwable.toString());
        });

        getCompositeDisposable().add(loginDisposable);


    }

    private Observable<JSONObject> doLogin() {
        dialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("C_USERNAME", username.get());
        param.put("C_PASSWORD", password.get());
        return Rx2AndroidNetworking.post(ConstantNetwork.LOGIN)
                .addBodyParameter(param)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface LoginListener {
        void onSuccess();

        void onError(String cause);
    }
}
