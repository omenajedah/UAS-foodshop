package com.firman.ecommerce.foodshop.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by Firman on 11/17/2018.
 */
public class SessionHandler {

    public static final String KEY_CUSERNAME = "KEY_CUSERNAME";
    public static final String KEY_CPASSWORD = "KEY_CPASSWORD";
    public static final String KEY_CNAME = "KEY_CNAME";
    public static final String KEY_CADDRESS = "KEY_CADDRESS";
    public static final String KEY_CMAIL = "KEY_CMAIL";
    public static final String KEY_CPHONE = "KEY_CPHONE";
    public static final String KEY_NSALDO = "KEY_NSALDO";
    public static final String KEY_CBACKGROUNDIMAGE = "KEY_CBACKGROUNDIMAGE";
    public static final String KEY_CPROFILEIMAGE = "KEY_CPROFILEIMAGE";



    public static final String KEY_CSELLERID = "KEY_CSELLERID";
    public static final String KEY_CSELLERNAME = "KEY_CSELLERNAME";
    public static final String KEY_CSELLERALIAS = "KEY_CSELLERALIAS";
    public static final String KEY_CSELLERADDR = "KEY_CSELLERADDR";



    private final SharedPreferences preferences;

    public SessionHandler(Context context) {
        this(context, context.getPackageName());
    }

    public SessionHandler(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void logout() {
        preferences.edit().clear().apply();
    }

    public void login(String c_USERNAME, String c_PASSWORD, String c_NAME, String c_ADDRESS,
                      String c_MAIL, String c_PHONE, long N_SALDO) {
        putString(KEY_CUSERNAME, c_USERNAME);
        putString(KEY_CPASSWORD, c_PASSWORD);
        putString(KEY_CNAME, c_NAME);
        putString(KEY_CADDRESS, c_ADDRESS);
        putString(KEY_CMAIL, c_MAIL);
        putString(KEY_CPHONE, c_PHONE);
        put(KEY_NSALDO, N_SALDO);

    }

    @Nullable
    public LoginProfile getProfile() {
        if (preferences.contains(KEY_CUSERNAME) && preferences.contains(KEY_CPASSWORD)) {
            return new LoginProfile(getString(KEY_CUSERNAME, null),
                    getString(KEY_CPASSWORD, null), getString(KEY_CNAME, null),
                    getString(KEY_CADDRESS, null), getString(KEY_CMAIL, null),
                    getString(KEY_CPHONE, null), get(KEY_NSALDO, 0L),
                    getString(KEY_CBACKGROUNDIMAGE, null), getString(KEY_CPROFILEIMAGE, null),
                    getString(KEY_CSELLERID, null));
        }
        return null;
    }

    public boolean isLogin() {
        return getProfile() != null;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void put(String key, boolean value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void put(String key, int value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void put(String key, float value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public void put(String key, Set<String> value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putStringSet(key, value);
        edit.apply();
    }

    public void put(String key, long value) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public boolean get(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public float get(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public long get(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public Set<String> get(String key, Set<String> defaultValue) {
        return preferences.getStringSet(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove(key);
        edit.apply();
    }

    public static class LoginProfile {
        private String C_USERNAME;
        private String C_PASSWORD;
        private String C_NAME;
        private String C_ADDRESS;
        private String C_MAIL;
        private String C_PHONE;
        private double N_SALDO;
        private String C_BACKGROUND_IMAGE;
        private String C_PROFILE_IMAGE;

        private String C_SELLER_ID;
        private String C_SELLER_NAME;
        private String C_SELLER_ALIAS;
        private String C_SELLER_ADDR;
        private String C_SELLER_MAIL;
        private String C_SELLER_PHONE;
        private String C_SELLER_BACKGROUND_IMAGE;
        private String C_SELLER_PROFILE_IMAGE;

        public LoginProfile(String c_USERNAME, String c_PASSWORD, String c_NAME, String c_ADDRESS,
                            String c_MAIL, String c_PHONE, double n_SALDO, String c_BACKGROUND_IMAGE,
                            String c_PROFILE_IMAGE, String c_SELLER_ID) {
            C_USERNAME = c_USERNAME;
            C_PASSWORD = c_PASSWORD;
            C_NAME = c_NAME;
            C_ADDRESS = c_ADDRESS;
            C_MAIL = c_MAIL;
            C_PHONE = c_PHONE;
            N_SALDO = n_SALDO;
            C_BACKGROUND_IMAGE = c_BACKGROUND_IMAGE;
            C_PROFILE_IMAGE = c_PROFILE_IMAGE;
            C_SELLER_ID = c_SELLER_ID;
        }

        public LoginProfile(double n_SALDO, String c_SELLER_ID, String c_SELLER_NAME,
                            String c_SELLER_ALIAS, String c_SELLER_ADDR, String c_SELLER_MAIL,
                            String c_SELLER_PHONE, String c_SELLER_BACKGROUND_IMAGE,
                            String c_SELLER_PROFILE_IMAGE) {
            N_SALDO = n_SALDO;
            C_SELLER_ID = c_SELLER_ID;
            C_SELLER_NAME = c_SELLER_NAME;
            C_SELLER_ALIAS = c_SELLER_ALIAS;
            C_SELLER_ADDR = c_SELLER_ADDR;
            C_SELLER_MAIL = c_SELLER_MAIL;
            C_SELLER_PHONE = c_SELLER_PHONE;
            C_SELLER_BACKGROUND_IMAGE = c_SELLER_BACKGROUND_IMAGE;
            C_SELLER_PROFILE_IMAGE = c_SELLER_PROFILE_IMAGE;
        }

        public String getC_USERNAME() {
            return C_USERNAME;
        }

        public String getC_PASSWORD() {
            return C_PASSWORD;
        }

        public String getC_NAME() {
            return C_NAME;
        }

        public String getC_ADDRESS() {
            return C_ADDRESS;
        }

        public String getC_MAIL() {
            return C_MAIL;
        }

        public String getC_PHONE() {
            return C_PHONE;
        }

        public double getN_SALDO() {
            return N_SALDO;
        }

        public String getC_BACKGROUND_IMAGE() {
            return C_BACKGROUND_IMAGE;
        }

        public String getC_PROFILE_IMAGE() {
            return C_PROFILE_IMAGE;
        }

        public String getC_SELLER_ID() {
            return C_SELLER_ID;
        }

        public String getC_SELLER_NAME() {
            return C_SELLER_NAME;
        }

        public String getC_SELLER_ALIAS() {
            return C_SELLER_ALIAS;
        }

        public String getC_SELLER_ADDR() {
            return C_SELLER_ADDR;
        }

        public String getC_SELLER_MAIL() {
            return C_SELLER_MAIL;
        }

        public String getC_SELLER_PHONE() {
            return C_SELLER_PHONE;
        }

        public String getC_SELLER_BACKGROUND_IMAGE() {
            return C_SELLER_BACKGROUND_IMAGE;
        }

        public String getC_SELLER_PROFILE_IMAGE() {
            return C_SELLER_PROFILE_IMAGE;
        }
    }
}
