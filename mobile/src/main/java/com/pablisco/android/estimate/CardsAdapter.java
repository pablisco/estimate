package com.pablisco.android.estimate;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.ArrayRes;
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

    public CardsAdapter(Context context, @ArrayRes int itemArray) {
        this(context.getResources().getTextArray(itemArray));
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
        }

        public CardViewBinding getDataBinding() {
            return dataBinding;
        }
    }
}
