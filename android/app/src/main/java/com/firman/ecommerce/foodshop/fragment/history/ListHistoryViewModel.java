package com.firman.ecommerce.foodshop.fragment.history;

import com.firman.ecommerce.foodshop.pojo.History;

/**
 * Created by Firman on 12/5/2018.
 */
public class ListHistoryViewModel {

    private final History history;

    public ListHistoryViewModel(History history) {
        this.history = history;
    }

    public History getHistory() {
        return history;
    }
}
