package com.firman.ecommerce.foodshop.fragment.history;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firman.ecommerce.foodshop.base.BaseRecyclerAdapter;
import com.firman.ecommerce.foodshop.base.BaseViewHolder;
import com.firman.ecommerce.foodshop.databinding.ListHistoryBinding;
import com.firman.ecommerce.foodshop.pojo.History;

import java.util.List;

/**
 * Created by Firman on 12/5/2018.
 */
public class HistoryAdapter extends BaseRecyclerAdapter<History> {

    public HistoryAdapter(List<History> originalList) {
        super(originalList);
    }

    @Override
    public boolean onSearch(String filter, History history) throws Exception {
        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListHistoryBinding binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    class HistoryViewHolder extends BaseViewHolder<ListHistoryBinding> {

        public HistoryViewHolder(ListHistoryBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            binding.setVm(new ListHistoryViewModel(showList.get(position)));
            binding.executePendingBindings();
        }
    }
}
