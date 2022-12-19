package com.utkarshnew.android.home.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.utkarshnew.android.Model.ExtendValidity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;

import java.util.List;

/**
 * Created by appsquadz on 25/9/17.
 */

public class ExtendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    public List<ExtendValidity> planlist;

    private BottomSheetDialog bottomSheetDialog;


    public ExtendAdapter(Context context, List<ExtendValidity> planlist, BottomSheetDialog bottomSheetDialog) {
        this.context = context;
        this.planlist = planlist;
        this.bottomSheetDialog = bottomSheetDialog;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topup_list_adapter, parent, false);
        return new StreamHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sholder, final int position) {

        try {

            final StreamHolder holder = (StreamHolder) sholder;
            holder.price.setText("₹ " + planlist.get(holder.getAdapterPosition()).getPrice() + ".00/-");

            holder.days.setText(" for " + planlist.get(holder.getAdapterPosition()).getValidity() + " days");

            if (planlist.get(holder.getAdapterPosition()).isIs_select()) {
                // planlist.get(holder.getAdapterPosition()).setIs_select(false);

                holder.selected.setImageResource(R.mipmap.topup_selected);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.price.setTextColor(context.getResources().getColor(R.color.white, null));
                    holder.days.setTextColor(context.getResources().getColor(R.color.white, null));
                } else {
                    holder.days.setTextColor(context.getResources().getColor(R.color.white));
                    holder.price.setTextColor(context.getResources().getColor(R.color.white));
                }
                holder.select_plan.setImageResource(R.drawable.radio_select_plan);
            } else {
                //planlist.get(holder.getAdapterPosition()).setIs_select(false);
                holder.selected.setImageResource(R.mipmap.topup_default);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    holder.days.setTextColor(context.getResources().getColor(R.color.shadowMsgInput, null));
                    holder.price.setTextColor(context.getResources().getColor(R.color.black, null));
                } else {
                    holder.days.setTextColor(context.getResources().getColor(R.color.shadowMsgInput));
                    holder.price.setTextColor(context.getResources().getColor(R.color.black));
                }
                holder.select_plan.setImageResource(R.drawable.radio_deselct_plan);
            }
            holder.layout_select.setOnClickListener(new OnSingleClickListener(() -> {
                for (int j = 0; j < planlist.size(); j++) {
                    if (planlist.get(j).isIs_select()) {
                        planlist.get(j).setIs_select(false);
                    }
                }
                planlist.get(holder.getAdapterPosition()).setIs_select(true);
                notifyDataSetChanged();
                return null;
            }));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if (planlist != null && planlist.size() > 0)
            return planlist.size();
        else return 0;
    }


    public class StreamHolder extends RecyclerView.ViewHolder {
        TextView price, days;
        ImageView select_plan, selected;
        RelativeLayout layout_select;

        public StreamHolder(View itemView) {
            super(itemView);

            days = itemView.findViewById(R.id.days);
            price = itemView.findViewById(R.id.price);
            select_plan = itemView.findViewById(R.id.select_plan);
            selected = itemView.findViewById(R.id.selected);
            layout_select = itemView.findViewById(R.id.layout_select);


        }
    }

}
