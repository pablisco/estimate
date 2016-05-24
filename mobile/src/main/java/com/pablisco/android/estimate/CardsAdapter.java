package com.pablisco.android.estimate;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private final CharSequence[] items;

    CardsAdapter(CharSequence[] items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setCardText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CharSequence cardText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onCardSelected);
        }

        @SuppressWarnings({"UnusedParameters", "unused"})
        void onCardSelected(View view) {
            Activity activity = findActivityFor(view);
            Intent intent = new Intent(activity, ResultActivity.class);
            intent.putExtra(ResultActivity.EXTRA_ESTIMATION, cardText);
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
        }

        void setCardText(CharSequence cardText) {
            this.cardText = cardText;
            ((TextView)itemView).setText(cardText);
        }
    }

    @Nullable
    private static Activity findActivityFor(View view) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper) {
            while(!(context instanceof Activity)) {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else {
            throw new IllegalStateException("No activity found for " + view);
        }
    }
}
