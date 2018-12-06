package com.firman.ecommerce.foodshop.common.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.firman.ecommerce.foodshop.common.db.Tables.TBL_CART;


/**
 * Created by Firman on 11/17/2018.
 */
public class AndroidDatabase extends SQLiteOpenHelper  {

    private static final String DBName = "FoodShop";
    private static final int version = 1;

    AndroidDatabase(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DBName, null, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL = "CREATE TABLE "+TBL_CART+" (N_ITENO INT(10) NOT NULL PRIMARY KEY," +
                "V_ITNAM VARCHAR(40) NOT NULL," +
                "C_IMAGE_PATH CHAR(32) DEFAULT NULL," +
                "N_PRICE DOUBLE(13,0) DEFAULT NULL," +
                "N_QOH DOUBLE(12,2) DEFAULT NULL," +
                "N_BOOK DOUBLE(12,2) DEFAULT NULL," +
                "V_NOTES VARCHAR(40) DEFAULT NULL," +
                "C_SELLER_NAME VARCHAR(40) DEFAULT NULL)";
        sqLiteDatabase.execSQL(SQL);


        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(SQL+ "");
        onCreate(sqLiteDatabase);
    }





}
