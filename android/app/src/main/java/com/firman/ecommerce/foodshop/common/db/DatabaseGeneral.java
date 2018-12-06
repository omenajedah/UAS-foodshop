package com.firman.ecommerce.foodshop.common.db;

import android.content.Context;

/**
 * Created by Firman on 11/17/2018.
 */
public class DatabaseGeneral extends Database {

    private final String TAG = DatabaseGeneral.class.getSimpleName();

    private static DatabaseGeneral instance;

    private DatabaseGeneral(Context context) {
        super(context);
    }

    public static DatabaseGeneral from(Context context) {
        synchronized (DatabaseGeneral.class) {
            if (instance == null)
                instance = new DatabaseGeneral(context);
        }
        return instance;
    }
}
