package com.firman.ecommerce.foodshop.common.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Firman on 2/10/2018.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONTagAnnotation {

    enum JSON {
        JSONObject,
        JSONArray
    }

    JSON type() default JSON.JSONObject;
}
