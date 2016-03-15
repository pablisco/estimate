package com.pablisco.android.estimate;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pablisco.android.estimate.databinding.CardViewBinding;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private final CharSequence[] items;

    public CardsAdapter(CharSequence[] items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getDataBinding().setCardText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardViewBinding dataBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            dataBinding = DataBindingUtil.bind(itemView);
            dataBinding.setViewHolder(this);
        }

        public CardViewBinding getDataBinding() {
            return dataBinding;
        }

        @SuppressWarnings({"UnusedParameters", "unused"})
        public void onCardSelected(View view) {
            Context context = dataBinding.getRoot().getContext();
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra(ResultActivity.KEY_ESTIMATION, dataBinding.getCardText());
            context.startActivity(intent);
        }

    }
}
