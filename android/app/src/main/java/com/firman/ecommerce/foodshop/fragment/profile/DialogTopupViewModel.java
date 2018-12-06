package com.firman.ecommerce.foodshop.fragment.profile;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

/**
 * Created by Firman on 12/2/2018.
 */
public class DialogTopupViewModel {

    private ObservableField<String> amount = new ObservableField<>("10000");
    private final DialogTopupListener listener;

    public DialogTopupViewModel(DialogTopupListener listener) {
        this.listener = listener;
    }

    public ObservableField<String> getAmount() {
        return amount;
    }

    public TextWatcher textWatcher(TextInputEditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable sequence) {
                if (sequence.toString().trim().length() < 5)
                    editText.setError("Minimum Topup is Rp. 10.000");
                else
                    editText.setError(null);
                if (TextUtils.isEmpty(sequence.toString().trim()) || sequence.toString().trim().length() == 0) {
                    amount.set("10000");
                } else if (sequence.toString().trim().length() > 0 && sequence.toString().trim().startsWith("0")) {
                    amount.set("10000");
                } else if (!amount.get().equals(sequence.toString().trim())) {
                    amount.set(sequence.toString().trim());
                }
            }
        };
    }

    public void onTopupClick() {
        Rx2AndroidNetworking.post(ConstantNetwork.TOPUP)
                .addBodyParameter("N_SALDO", amount.get())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optBoolean("success")) {
                            if (listener != null)
                                listener.onTopupSuccess();
                        } else onError(new ANError("Topup Failed"));
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (listener != null)
                            listener.onTopupFailed(anError);
                    }
                });
    }

    public interface DialogTopupListener {
        void onTopupSuccess();
        void onTopupFailed(Throwable cause);
    }


}
