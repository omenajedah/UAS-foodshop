package com.firman.ecommerce.foodshop.fragment.register;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.firman.ecommerce.foodshop.base.BaseViewModel;

/**
 * Created by Firman on 11/18/2018.
 */
public class RegisterViewModel extends BaseViewModel {

    private ObservableBoolean seller = new ObservableBoolean();
    private ObservableField<String> username = new ObservableField<>("");
    private ObservableField<String> password = new ObservableField<>("");
    private ObservableField<String> name = new ObservableField<>("");
    private ObservableField<String> sellerName = new ObservableField<>("");
    private ObservableField<String> sellerAlias = new ObservableField<>("");

    private final RegisterListener listener;

    public RegisterViewModel(Context context, RegisterListener listener) {
        super(context);
        this.listener=listener;
    }

    public ObservableBoolean getSeller() {
        return seller;
    }

    public ObservableField<String> getUsername() {
        username.set(username.get().trim());
        return username;
    }

    public ObservableField<String> getPassword() {
        password.set(password.get().trim());

        return password;
    }

    public ObservableField<String> getName() {
        name.set(name.get().trim());

        return name;
    }

    public ObservableField<String> getSellerName() {
        sellerName.set(sellerName.get().trim());

        return sellerName;
    }

    public ObservableField<String> getSellerAlias() {
        sellerAlias.set(sellerAlias.get().trim());

        return sellerAlias;
    }

    public void setSeller(ObservableBoolean seller) {
        this.seller = seller;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public void setSellerName(ObservableField<String> sellerName) {
        this.sellerName = sellerName;
    }

    public void setSellerAlias(ObservableField<String> sellerAlias) {
        this.sellerAlias = sellerAlias;
    }

    public interface RegisterListener {

    }
}
