package com.firman.ecommerce.foodshop.base;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.firman.ecommerce.foodshop.common.SessionHandler;
import com.firman.ecommerce.foodshop.common.db.DatabaseGeneral;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Firman on 11/17/2018.
 */
public abstract class BaseViewModel extends ViewModel {

    protected final String TAG = getClass().getSimpleName();

    protected ProgressDialog progressDialog;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<Context> mContext;

    private final SessionHandler generalSession;
    private final SessionHandler privateSession;

    private final DatabaseGeneral databaseGeneral;

    public BaseViewModel(Context context) {
        this.mContext = new WeakReference<>(context);
        this.generalSession = new SessionHandler(context);
        this.privateSession = new SessionHandler(context, getClass().getSimpleName());
        this.progressDialog = new ProgressDialog(context);
        this.mCompositeDisposable = new CompositeDisposable();
        this.databaseGeneral = DatabaseGeneral.from(context);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public DatabaseGeneral dbGeneral() {
        return databaseGeneral;
    }

    public SessionHandler getGeneralSession() {
        return generalSession;
    }

    public SessionHandler getPrivateSession() {
        return privateSession;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected Context getContext() {
        return this.mContext.get();
    }

}
