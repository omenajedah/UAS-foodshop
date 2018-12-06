package com.firman.ecommerce.foodshop.base;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Firman on 11/22/2018.
 */
public class BindingHolder {

    @BindingAdapter("setBitmap")
    public static void setBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("setRecycleAdapter")
    public static void setRecycleAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("htmlData")
    public static void loadHtml(WebView webView, String html) {
        if (html != null)
            webView.loadData(html, "text/html", "UTF-8");
        else
            webView.loadData("No Description Added", "text/html", "UTF-8");

    }

    @BindingAdapter("setTextWatcher")
    public static void setTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}
