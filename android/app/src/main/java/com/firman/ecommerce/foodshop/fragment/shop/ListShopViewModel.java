package com.firman.ecommerce.foodshop.fragment.shop;

import com.firman.ecommerce.foodshop.pojo.Product;

/**
 * Created by Firman on 11/22/2018.
 */
public class ListShopViewModel {

    private final Product product;

    public ListShopViewModel(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
