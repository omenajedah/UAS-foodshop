package com.firman.ecommerce.foodshop.common.reflect.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Firman on 2/9/2018.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAnnotation {

    enum DateType {
        TYPE_DATE,
        TYPE_DATE_TIME,
        TYPE_CUSTOM
    }


    DateType type() default DateType.TYPE_DATE;
    String src() default "yyyy-MM-dd";
    String customFormat() default "";

}
