package com.firman.ecommerce.foodshop.fragment.cart;

import android.databinding.ObservableInt;
import android.databinding.ObservableLong;

/**
 * Created by Firman on 12/4/2018.
 */
public class DialogConfirmationViewModel {

    private ObservableLong N_SALDO = new ObservableLong();
    private ObservableInt totalItems = new ObservableInt();
    private ObservableLong totalPrice = new ObservableLong();
    private final DialogConfirmationListener listener;

    public DialogConfirmationViewModel(DialogConfirmationListener listener) {
        this.listener = listener;
    }

    public ObservableLong getN_SALDO() {
        return N_SALDO;
    }

    public ObservableInt getTotalItems() {
        return totalItems;
    }

    public ObservableLong getTotalPrice() {
        return totalPrice;
    }

    public void setN_SALDO(long n_SALDO) {
        N_SALDO.set(n_SALDO);
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public void onCancelClick() {
        if (listener != null)
            listener.onCancel();
    }

    public void onConfirmClick() {
        if (listener != null)
            listener.onConfirmation();
    }

    public interface DialogConfirmationListener {
        void onCancel();
        void onConfirmation();
    }
}

