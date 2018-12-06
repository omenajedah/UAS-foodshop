package com.firman.ecommerce.foodshop.fragment.history;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.firman.ecommerce.foodshop.base.BaseViewModel;
import com.firman.ecommerce.foodshop.common.network.ConstantNetwork;
import com.firman.ecommerce.foodshop.pojo.History;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Firman on 11/17/2018.
 */
public class HistoryViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {

    private final HistoryListener historyListener;

    public HistoryViewModel(Context context, HistoryListener historyListener) {
        super(context);
        this.historyListener = historyListener;
    }

    public void refresh() {
        historyListener.onLoad();
        getCompositeDisposable().clear();
        getCompositeDisposable().add(
                getHistory().subscribe(histories -> {
                    if (historyListener != null)
                        historyListener.onRefresh(histories);
                }, throwable -> {
                    throwable.printStackTrace();
                    if (historyListener != null)
                        historyListener.onError(throwable);
                })
        );
    }

    private Observable<List<History>> getHistory() {
        return Rx2AndroidNetworking.post(ConstantNetwork.HISTORY)
                .build()
                .getJSONObjectObservable()
                .map(jsonObject -> {
                    List<History> histories = new ArrayList<>();

                    if (jsonObject.getInt("RowCount") <=0)
                        return histories;

                    JSONArray array = jsonObject.getJSONArray("DataRow");
                    for (int i=0;i<array.length();i++) {
                        JSONObject item = array.getJSONObject(i);
                        histories.add(History.fromJson(item));
                    }

                    return histories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    public interface HistoryListener {
        void onLoad();
        public void onRefresh(List<History> histories);
        public void onError(Throwable cause);
    }
}
