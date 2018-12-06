package com.firman.ecommerce.foodshop.fragment.cart;

import com.firman.ecommerce.foodshop.pojo.Seller;

/**
 * Created by Firman on 12/1/2018.
 */
public class ListSellerCartViewModel {

    private final Seller seller;

    public ListSellerCartViewModel(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
