package com.firman.ecommerce.foodshop.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firman.ecommerce.foodshop.common.reflect.ObjectParser;
import com.firman.ecommerce.foodshop.common.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 11/17/2018.
 */
public abstract class Database {

    private final String TAG = Database.class.getSimpleName();
    private final AndroidDatabase androidDatabase;
    private volatile int openedDbCount = 0;
    private SQLiteDatabase db;

    Database(Context context) {
        androidDatabase = new AndroidDatabase(context, null);
        Log.i(TAG, "Instantiate Android database on " + Thread.currentThread().toString());
//        Log.i(TAG, "AUTO NUMBER " + autoNumber(TableNames.TBL_CART_PRE_OCS_HD));
    }

    public void speedOpen() {
        open();
        db.beginTransaction();
    }

    public void speedClose() {
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    private synchronized void open() {
        openedDbCount++;
        if (openedDbCount == 1) {
            db = androidDatabase.getWritableDatabase();
        }
        Log.d(TAG, ".open OpenedCount="+openedDbCount);
    }

    private synchronized void close() {
        openedDbCount--;
        if (openedDbCount == 0) {
            db.close();
        }
        Log.d(TAG, ".close OpenedCount="+openedDbCount);
    }

    public long speedInsert(@Tables String table, ContentValues values) {
        return db.insert(table, null, values);
    }

    public int speedUpdate(@Tables String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        return db.update(table, contentValues, whereClause, whereArgs);
    }

    public final void execute(String sql) {
        open();
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
        close();
    }

    @Nullable
    public <T> T query(@Tables String sql, @NonNull Class<T> tClass) {
        open();
        db.beginTransaction();
        Object object = null;
        try {
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                if (tClass == String.class) {
                    object = cursor.getString(0);
                } else if (tClass == Integer.class) {
                    object = cursor.getInt(0);
                } else if (tClass == Long.class) {
                    object = cursor.getLong(0);
                } else if (tClass == Double.class) {
                    object = cursor.getDouble(0);
                } else if (tClass == Float.class) {
                    object = cursor.getFloat(0);
                } else {
                    object = ObjectParser.get().getObject(tClass);
                    ObjectParser.get().assignValue(cursor, object);
                }
            }

            cursor.close();
            db.setTransactionSuccessful();
            Log.i(TAG, String.format("query %s, returned object %s done! ", sql, object.getClass().getName()));
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();

        return (T) object;
    }

    public <T> List<T> queryMulti(String sql, @NonNull Class<T> tClass) {
        open();
        db.beginTransaction();
        List<Object> objectList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    ObjectParser op = ObjectParser.get();

                    Object object = op.getObject(tClass);
                    op.assignValue(cursor, object);
                    objectList.add(object);
                } while (cursor.moveToNext());

                db.setTransactionSuccessful();

                Log.i(TAG, String.format("queryMulti %s, returned object %s done! ", sql, tClass.getName()));
            }

            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();

        return (List<T>) objectList;
    }

    public long insert(@Tables String table, ContentValues values) {
        open();
        db.beginTransaction();
        long id = 0;
        try {
            id = db.insert(table, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
        close();
        return id;
    }

    public long[] insertMultiple(@Tables String table, List<ContentValues> contentValuesList) {
        long[] rows = new long[contentValuesList.size()];
        open();
        db.beginTransaction();
        try {
            for (int i = 0; i < contentValuesList.size(); i++) {
                ContentValues values = contentValuesList.get(i);
                long row = db.insert(table, null, values);
                rows[i] = row;

            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();
        return rows;
    }

    public int update(@Tables String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        open();
        db.beginTransaction();
        int id = 0;
        try {
            id = db.update(table, contentValues, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();
        return id;
    }

    public int[] updateMultiple(@Tables String table, List<ContentValues> t,
                                String whereClause, String[] whereArgs) {
        int[] rows = new int[t.size()];
        open();

        db.beginTransaction();
        try {
            for (int i = 0; i < t.size(); i++) {
                ContentValues values = t.get(i);
                if (values != null) {
                    int row = db.update(table, values, whereClause, whereArgs);
                    rows[i] = row;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();
        return rows;
    }

    public int delete(@Tables String table, String whereClause, String[] whereArgs) {
        open();
        db.beginTransaction();
        int id = 0;
        try {
            id = db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.endTransaction();
        close();
        return id;
    }

    public int getCountFrom(@Tables String table) {
        open();
        int jml = 0;
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + table, null);
            cursor.moveToFirst();
            jml = cursor.getInt(0);
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }

        db.endTransaction();
        close();

        return jml;
    }

    protected List<ContentValues> query(String sql, String[] selectionArgs) {
        open();
        List<ContentValues> values = new ArrayList<>();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(sql, selectionArgs);
            cursor.moveToFirst();
            Log.d(TAG, "Cursor size="+cursor.getCount());
            if (cursor.getCount() > 0) {
                do {
                    ContentValues value = new ContentValues();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        Log.d(TAG, "name="+cursor.getColumnName(i)+", value="+cursor.getString(i));
                        switch (cursor.getType(i)) {
                            case Cursor.FIELD_TYPE_BLOB:
                                value.put(cursor.getColumnName(i), cursor.getBlob(i));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                value.put(cursor.getColumnName(i), cursor.getDouble(i));
                                break;
                            case Cursor.FIELD_TYPE_INTEGER:
                                value.put(cursor.getColumnName(i), cursor.getLong(i));
                                break;
                            case Cursor.FIELD_TYPE_NULL:
                                value.put(cursor.getColumnName(i), (String) null);
                                break;
                            case Cursor.FIELD_TYPE_STRING:
                                value.put(cursor.getColumnName(i), cursor.getString(i));
                                break;

                        }
                    }
                    values.add(value);
                } while (cursor.moveToNext());
            }


            cursor.close();

            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }

        db.endTransaction();
        close();

        return values;
    }

    public String datetime() {
        return Utils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    }

    public String date() {
        return Utils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd");
    }

    public String autoNumber(@Tables String table) {
        open();
        int currentNumber = 0;
        db.beginTransaction();
        try {
            currentNumber = query("SELECT COUNT(*) FROM " + table, Integer.class) +1;
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.endTransaction();
        close();

        return Utils.formatDate(Utils.parseDate(System.currentTimeMillis()), "yyMMdd") +
                "000" + currentNumber;
    }

    protected String sqlBuilder(String sql, Object... args) {
        if (sql == null || args.length == 0) return null;

        for (int i = 0; i < args.length; i++) {
            sql = sql.replace("[" + i + "]", String.valueOf(args[i]));
        }
        return sql;
    }
}
