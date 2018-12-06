package com.firman.ecommerce.foodshop.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.firman.ecommerce.foodshop.common.SessionHandler;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Firman on 11/18/2018.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(chain -> {

                    SessionHandler sessionHandler = new SessionHandler(this);
                    Request request = chain.request();

                    if (request.header("Cookie") == null) {

                        if (request.body() != null)
                            request = request.newBuilder()
                                    .url(request.url())
                                    .addHeader("Cookie", sessionHandler.getString("KEY_COOKIE", ""))
                                    .post(request.body())
                                    .build();
                        else
                            request = request.newBuilder()
                                    .url(request.url())
                                    .addHeader("Cookie", sessionHandler.getString("KEY_COOKIE", ""))
                                    .get()
                                    .build();
                    }

                    Response response = chain.proceed(request);

                    if (!response.isSuccessful()) {

                        if (sessionHandler.isLogin()) {
                            SessionHandler.LoginProfile loginProfile = sessionHandler.getProfile();
                            RequestBody body = new FormBody.Builder()
                                    .add("C_USERNAME", loginProfile.getC_USERNAME())
                                    .add("C_PASSWORD", loginProfile.getC_PASSWORD())
                                    .build();


                            //prepare an authentication request
                            Request authentication = request.newBuilder()
                                    .url(ConstantNetwork.LOGIN)
                                    .post(body)
                                    .build();

                            Response authenticationResponse = chain.proceed(authentication);

                            if (authenticationResponse.isSuccessful())
                                sessionHandler.putString("KEY_COOKIE",
                                        authenticationResponse.header("set-cookie", null));
                        }

                        Request oldRequest;
                        if (request.body() != null)
                             oldRequest = request.newBuilder()
                                .url(request.url())
                                .addHeader("Cookie", sessionHandler.getString("KEY_COOKIE", ""))
                                .post(request.body())
                                .build();
                        else
                            oldRequest = request.newBuilder()
                                    .url(request.url())
                                    .addHeader("Cookie", sessionHandler.getString("KEY_COOKIE", ""))
                                    .get()
                                    .build();

                        response = chain.proceed(oldRequest);
                    }

                    return response;
                });
//                .cookieJar(new CookieJar() {
//                    private List<Cookie> cookies;
//
//                    @Override
//                    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
//                        this.cookies =  cookies;
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
//                        if (cookies != null)
//                            return cookies;
//                        return new ArrayList<>();
//
//                    }
//                });

        AndroidNetworking.initialize(this, builder.build());
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
    }
}
