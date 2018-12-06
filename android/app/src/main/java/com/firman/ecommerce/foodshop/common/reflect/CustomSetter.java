package com.firman.ecommerce.foodshop.common.reflect;

import com.firman.ecommerce.foodshop.pojo.Category;
import com.firman.ecommerce.foodshop.pojo.Product;
import com.firman.ecommerce.foodshop.pojo.Seller;

/**
 * Created by Firman on 12/1/2018.
 */
public class CustomSetter {

    public static <E extends Product> void setCategory(E product, String fieldName, String value) {
        if (product.getCategory() == null)
            product.setCategory(new Category());

        if (fieldName.equals("C_CATEGORY_ID"))
            product.getCategory().setC_CATEGORY_ID(value);
        else if (fieldName.equals("C_CATEGORY_NAME"))
            product.getCategory().setC_CATEGORY_NAME(value);
    }

    public static <E extends Product> void setSeller(E product, String fieldName, String value) {
        if (product.getSeller() == null)
            product.setSeller(new Seller());

        if (fieldName.equals("C_SELLER_ID"))
            product.getSeller().setC_SELLER_ID(value);
        else if (fieldName.equals("C_SELLER_NAME"))
            product.getSeller().setC_SELLER_NAME(value);
    }
}
