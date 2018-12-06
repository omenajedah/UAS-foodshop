package com.firman.ecommerce.foodshop.common.reflect;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firman.ecommerce.foodshop.common.reflect.annotation.CustomSetterAnnotation;
import com.firman.ecommerce.foodshop.common.reflect.annotation.DateAnnotation;
import com.firman.ecommerce.foodshop.common.reflect.annotation.IntBooleanAnnotation;
import com.firman.ecommerce.foodshop.common.reflect.annotation.JSONTagAnnotation;
import com.firman.ecommerce.foodshop.common.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by Firman on 2/9/2018.
 * Class Helper for copying Object value into Content Values and Content Values into Object
 */
@SuppressWarnings("Unchecked")
public class ObjectParser {

    private static ObjectParser instance;
    private final String TAG = ObjectParser.class.getSimpleName();

    //avoid instantiation
    ObjectParser() {}

    public static ObjectParser get() {
        if (instance == null) {
            synchronized (ObjectParser.class) {
                if (instance == null) {
                    instance = new ObjectParser();
                }
            }
        }

        return instance;
    }

    //Parser From Content Values into T (Object)
    public <F> F from(ContentValues values, Class<F> fClass) {
        if (values == null || fClass == null) return null;

        Object o = getObject(fClass);

        if (o == null) return null;

        for (String key : values.keySet()) {
            try {

                Field field = fClass.getDeclaredField(key);
                field.setAccessible(true);
                if (field.getType().isPrimitive()) {

                    if (field.getType().equals(Integer.TYPE)) {
                        field.setInt(o, values.getAsInteger(key));

                    } else if (field.getType().equals(Long.TYPE)) {
                        field.setLong(o, values.getAsLong(key));

                    } else if (field.getType().equals(Float.TYPE)) {
                        field.setFloat(o, values.getAsFloat(key));

                    } else if (field.getType().equals(Double.TYPE)) {
                        field.setDouble(o, values.getAsDouble(key));

                    } else if (field.getType().equals(Boolean.TYPE)) {
                        field.setBoolean(o, values.getAsBoolean(key));
                    }
                } else {
                    Class type = field.getType();
                    if (type.getName().equals("java.lang.String")) {
                        field.set(o, values.getAsString(key));
                    } else if (type.getName().equals("java.util.Date") && values.getAsString(key) != null) {
                        if (field.isAnnotationPresent(DateAnnotation.class)) {
                            try {
                                Date date;
                                if (field.getAnnotation(DateAnnotation.class).type() == DateAnnotation.DateType.TYPE_DATE_TIME) {
                                    date = Utils.parseDate(values.getAsString(key), "yyyy-MM-dd HH:mm:ss");
                                } else if (field.getAnnotation(DateAnnotation.class).type() == DateAnnotation.DateType.TYPE_CUSTOM){
                                    date = Utils.parseDate(values.getAsString(key), field.getAnnotation(DateAnnotation.class).customFormat());
                                } else {
                                    date = Utils.parseDate(values.getAsString(key), "yyyy-MM-dd");
                                }

                                field.set(o, date);

                            } catch (Exception e) {
                                Log.e(TAG + ".from(CV)", "Error parse date " + e.toString());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG + ".from(CV)", "Error " + e.toString());
            }
        }
        return (F) o;
    }


    //Parser From Object into Content Values
    public <T> ContentValues getCVFrom(T t, String... selectedField) {
        if (t == null) return null;

        ContentValues values = new ContentValues();

        for (Field field : t.getClass().getDeclaredFields()) {

            try {
                field.setAccessible(true);
                Class type = field.getType();
                if (type.isPrimitive()) {
                    if (type.equals(Integer.TYPE)) {
                        values.put(field.getName(), field.getInt(t));
                    } else if (type.equals(Double.TYPE)) {
                        values.put(field.getName(), field.getDouble(t));
                    } else if (type.equals(Float.TYPE)) {
                        values.put(field.getName(), field.getFloat(t));
                    } else if (type.equals(Long.TYPE)) {
                        values.put(field.getName(), field.getLong(t));
                    } else if (type.equals(Boolean.TYPE)) {
                        values.put(field.getName(), field.getBoolean(t));
                    }
                } else {
                    if (field.isAnnotationPresent(DateAnnotation.class)) {
                        String date;
                        if (field.getAnnotation(DateAnnotation.class).type() == DateAnnotation.DateType.TYPE_DATE_TIME) {
                            date = Utils.formatDate((Date) field.get(t), "yyyy-MM-dd HH:mm:ss");
                        } else {
                            date = Utils.formatDate((Date) field.get(t), "yyyy-MM-dd");
                        }
                        values.put(field.getName(), date);
                    } else if (field.isAnnotationPresent(JSONTagAnnotation.class)) {

                        if (field.getAnnotation(JSONTagAnnotation.class).type() == JSONTagAnnotation.JSON.JSONObject) {
                            JSONObject jsonObject = new JSONObject((String) field.get(t));
                            values.put(field.getName(), jsonObject.toString());
                        } else {
                            JSONArray jsonArray = new JSONArray((String) field.get(t));
                            values.put(field.getName(), jsonArray.toString());
                        }
                    } else {
                        values.put(field.getName(), (String) field.get(t));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG + ".getCVFrom(Object)", "Error " + e.toString());

            }
        }
        return values;
    }

    // instantiation method from class
    public <T> T getObject(@NonNull Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            Log.e(TAG + ".getObject(Class<?>)", "Error instantiate " + e.toString());
        }
        return null;
    }

    // method helper for set value from cursor
    public void assignValue(Cursor openedCursor, @NonNull Object object) {
        long started = System.currentTimeMillis();

        Class<?> instanceClass = null;
        Class superClass = null;
        Field field = null;
        for (int i = 0; i < openedCursor.getColumnCount(); i++) {
            switch (openedCursor.getType(i)) {
                case Cursor.FIELD_TYPE_BLOB:
                    set(object, openedCursor.getColumnName(i), openedCursor.getBlob(i));
                    break;
                case Cursor.FIELD_TYPE_FLOAT :
                    instanceClass = object.getClass();

                    try {
                        field = instanceClass.getDeclaredField(openedCursor.getColumnName(i));
                    } catch (NoSuchFieldException e) {
                        field = null;
                    }
                    superClass = instanceClass.getSuperclass();
                    while (field == null && superClass != null) {
                        try {
                            field = superClass.getDeclaredField(openedCursor.getColumnName(i));
                        } catch (NoSuchFieldException e) {
                            superClass = superClass.getSuperclass();
                        }
                    }

                    if (field == null) {
                        continue;
                    }

                    try {
                        field.setAccessible(true);
                        if (field.getType().equals(Float.TYPE)) {
                            field.setFloat(object, openedCursor.getFloat(i));
                        } else if (field.getType().equals(Double.TYPE)) {
                            field.setDouble(object, openedCursor.getDouble(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG + ".assignValue(CV)", "Error " + e.toString());
                    }


                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    instanceClass = object.getClass();
                    try {
                        field = instanceClass.getDeclaredField(openedCursor.getColumnName(i));
                    } catch (NoSuchFieldException e) {
                        field = null;
                    }

                    superClass = instanceClass.getSuperclass();
                    while (field == null && superClass != null) {
                        try {
                            field = superClass.getDeclaredField(openedCursor.getColumnName(i));
                        } catch (NoSuchFieldException e) {
                            superClass = superClass.getSuperclass();
                        }
                    }

                    if (field == null) {
                        continue;
                    }

                    try {
                        field.setAccessible(true);

                        if (field.getType().equals(Boolean.TYPE) && field.isAnnotationPresent(IntBooleanAnnotation.class)) {
                            Log.i(TAG, "intboolean name "+openedCursor.getColumnName(i)+" value "+openedCursor.getInt(i));
                            field.setBoolean(object, openedCursor.getInt(i) == 1);
                        } else if (field.getType().equals(Integer.TYPE)) {
                            field.setInt(object, openedCursor.getInt(i));
                        } else if (field.getType().equals(Long.TYPE)) {
                            field.setLong(object, openedCursor.getLong(i));
                        }
                    } catch (Exception e) {
                        Log.e(TAG + ".assignValue(CV)", "Error " + e.toString());
                    }

                    break;
                case Cursor.FIELD_TYPE_NULL:

                    set(object, openedCursor.getColumnName(i), null);
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    set(object, openedCursor.getColumnName(i), openedCursor.getString(i));
                    break;
            }
        }
    }

    private boolean set(Object object, String fieldName, String fieldValue) {
        Class<?> clazz = object.getClass();
        Class superClass;
        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        superClass = clazz.getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }

        if (field == null) {
            return false;
        }

        try {
            field.setAccessible(true);

            if (field.isAnnotationPresent(CustomSetterAnnotation.class)) {
                Class<?> setterClass = field.getAnnotation(CustomSetterAnnotation.class).setterClass();
                String methodName = field.getAnnotation(CustomSetterAnnotation.class).methodName();

                Method method = setterClass.getMethod(methodName, object.getClass(), String.class, String.class);

                if (method == null)
                    return false;

                method.invoke(null, object, fieldName, fieldValue);
                return true;
            }

            if (!field.getType().isInstance(fieldValue)) {

                if (field.isAnnotationPresent(DateAnnotation.class)) {
                    Date date = null;

                    if (field.getAnnotation(DateAnnotation.class).type() == DateAnnotation.DateType.TYPE_DATE_TIME) {
                        try {
                            date = Utils.parseDate(fieldValue, "yyyy-MM-dd HH:mm:ss");
                        } catch (Exception e) {
                            Log.e(TAG + ".set(Str)", "Error parse date " + e.toString());
                        }
                    } else {
                        try {
                            date = Utils.parseDate(fieldValue, "yyyy-MM-dd");
                        } catch (Exception e) {
                            Log.e(TAG + ".set(Str)", "Error parse date " + e.toString());
                        }
                    }
                    field.set(object, date);
                } else if (field.isAnnotationPresent(JSONTagAnnotation.class)) {

                    if (field.getAnnotation(JSONTagAnnotation.class).type() == JSONTagAnnotation.JSON.JSONObject) {
                        JSONObject jsonObject = new JSONObject(fieldValue);
                        field.set(object, jsonObject);
                    } else {
                        JSONArray jsonArray = new JSONArray(fieldValue);
                        field.set(object, jsonArray);
                    }
                }
                return true;
            }

            field.set(object, fieldValue);
            return true;
        } catch (Exception e) {
            Log.e(TAG + ".set(Str)", "Error " + e.toString());
        }

        return false;
    }

    private boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        Class superClass;
        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        superClass = clazz.getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }

        if (field == null) {
            return false;
        }

        try {
            field.setAccessible(true);
            field.set(object, fieldValue);
            return true;
        } catch (Exception e) {
            Log.e(TAG + ".set(Str)", "Error parse date " + e.toString());
        }

        return false;
    }

}
