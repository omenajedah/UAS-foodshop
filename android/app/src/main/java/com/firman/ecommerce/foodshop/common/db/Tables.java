package com.firman.ecommerce.foodshop.common.db;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.firman.ecommerce.foodshop.common.db.Tables.TBL_CART;


/**
 * Created by Firman on 12/1/2018.
 */

@StringDef({
        TBL_CART
})
@Retention(RetentionPolicy.SOURCE)
public @interface Tables {
    String TBL_CART = "TBL_CART";
}