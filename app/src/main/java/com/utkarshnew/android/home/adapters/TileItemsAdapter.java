package com.utkarshnew.android.home.adapters;

import static com.utkarshnew.android.Utils.Helper.convertDpToPixel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.home.interfaces.onButtonClicked;
import com.utkarshnew.android.Model.Courses.Cards;
import com.utkarshnew.android.R;

import java.util.ArrayList;

public class TileItemsAdapter extends RecyclerView.Adapter<TileItemsAdapter.MyViewHolder> {

    private ArrayList<Cards> cards;
    private Context context;
    public String contentType;
    int tilePos = 0;
    onButtonClicked buttonClicked;

    public TileItemsAdapter(Context context, String contentType, ArrayList<Cards> cards, onButtonClicked buttonClicked) {
        this.cards = cards;
        this.context = context;
        this.contentType = contentType;
        this.buttonClicked = buttonClicked;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parent;
        public LinearLayout tour_ll;
        public TextView tilesText;

        public MyViewHolder(View view) {
            super(view);
            tilesText = (TextView) view.findViewById(R.id.tilesTextTv);
            parent = (LinearLayout) view.findViewById(R.id.parentBottom);
            tour_ll = (LinearLayout) view.findViewById(R.id.tour_ll);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(6, 0, 6, 0);
            parent.setLayoutParams(layoutParams);
            tilesText.setPadding(Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)), Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)));
        }
    }

    @Override
    public TileItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_tiles, parent, false);
        return new TileItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TileItemsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Cards card = cards.get(position);

        holder.tilesText.setText(card.getTile_name());

        if (contentType.equals(card.getType() + card.getId())) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#000000"));
            gradientDrawable.setCornerRadius(60);
            holder.parent.setBackground(gradientDrawable);
            holder.tilesText.setTextColor(Color.WHITE);
            holder.tilesText.setTextColor(Color.WHITE);
        } else {
            holder.parent.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
            holder.tilesText.setTextColor(context.getResources().getColor(R.color.tile_inactive));
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentType = card.getType() + card.getId();
                tilePos = position;
                buttonClicked.onTitleClicked(card, cards, contentType, tilePos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

}
