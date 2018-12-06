package com.firman.ecommerce.foodshop.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Firman on 11/17/2018.
 */
public class Utils {

    public static String formatIDR(long idr) {
        DecimalFormat kursIDR = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIDR.setDecimalFormatSymbols(formatRp);

        return kursIDR.format(idr);
    }


    public static Date parseDate(long source) {
        return parseDate(Locale.getDefault(), source);
    }

    public static Date parseDate(Locale locale, long source) {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTimeInMillis(source);
        return calendar.getTime();
    }

    public static Date parseDate(String source, String pattern) throws ParseException {
        return parseDate(Locale.getDefault(), source, pattern);
    }

    public static Date parseDate(Locale locale, String source, String pattern) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat(pattern, locale);
        return parser.parse(source);
    }

    public static String formatDate(long source, String pattern) {
        return formatDate(Locale.getDefault(), source, pattern);
    }

    public static String formatDate(Locale locale, long source, String pattern) {
        return formatDate(locale, parseDate(source), pattern);
    }

    public static String formatDate(String source, String sourcePattern, String pattern) throws ParseException {
        return formatDate(Locale.getDefault(), source, sourcePattern, pattern);
    }

    public static String formatDate(Locale locale, String source, String sourcePattern, String pattern) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat(pattern, locale);
        return parser.format(parseDate(locale, source, sourcePattern));
    }

    public static String formatDate(Date source, String pattern) {
        return formatDate(Locale.getDefault(), source, pattern);
    }

    public static String formatDate(Locale locale, Date source, String pattern) {
        SimpleDateFormat parser = new SimpleDateFormat(pattern, locale);
        return parser.format(source);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, 0);
            }
        }
    }
}