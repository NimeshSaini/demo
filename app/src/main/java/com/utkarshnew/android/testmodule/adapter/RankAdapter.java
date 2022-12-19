package com.utkarshnew.android.testmodule.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.NonScrollRecyclerView;
import com.utkarshnew.android.testmodule.model.TopTenList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAdapter extends NonScrollRecyclerView.Adapter<RankAdapter.MyViewHolder> {
    Context context;
    List<TopTenList> topTenLists = new ArrayList<>();

    public RankAdapter(Context context, List<TopTenList> topTenLists) {
        this.context = context;
        this.topTenLists = topTenLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (topTenLists.get(position).getName() != null)
            holder.textname.setText(topTenLists.get(position).getName());


        holder.textrank.setText("Rank : " + topTenLists.get(position).getRank());
        holder.mark.setText("Marks : " + "" + topTenLists.get(holder.getAdapterPosition()).getMarks());

        if (position == 0) {
            holder.rankLL.setBackground(context.getResources().getDrawable(R.drawable.background_rank_one));
            holder.imgSuccess.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            holder.rankLL.setBackground(context.getResources().getDrawable(R.drawable.background_rank_two));
            holder.imgSuccess.setVisibility(View.VISIBLE);
        } else if (position == 2) {
            holder.rankLL.setBackground(context.getResources().getDrawable(R.drawable.background_rank_three));
            holder.imgSuccess.setVisibility(View.VISIBLE);
        } else {
            holder.rankLL.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
        }

        if (!TextUtils.isEmpty(topTenLists.get(position).getProfilePicture())) {
            holder.imgthumb.setVisibility(View.VISIBLE);
            holder.imageIVText.setVisibility(View.GONE);
            Glide.with(context).load(topTenLists.get(position).getProfilePicture())
                    .into(holder.imgthumb);
        } else {
            Drawable dr = Helper.GetDrawable(topTenLists.get(position).getName(), context,
                    topTenLists.get(position).getUserId());
            if (dr != null) {
                holder.imgthumb.setVisibility(View.GONE);
                holder.imageIVText.setVisibility(View.VISIBLE);
                holder.imageIVText.setImageDrawable(dr);
            } else {
                holder.imgthumb.setVisibility(View.VISIBLE);
                holder.imageIVText.setVisibility(View.GONE);
                holder.imgthumb.setImageResource(R.mipmap.default_pic);
            }
        }
    }

    @Override
    public int getItemCount() {
        return topTenLists.size();
    }


    public class MyViewHolder extends NonScrollRecyclerView.ViewHolder {
        TextView textname, textrank, mark;
        CircleImageView imgthumb, imgSuccess;
        ImageView imageIVText;
        LinearLayout rankLL;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgthumb = itemView.findViewById(R.id.imgthumb);
            textname = itemView.findViewById(R.id.textname);
            textrank = itemView.findViewById(R.id.textrank);
            imageIVText = itemView.findViewById(R.id.imageIVText);
            mark = itemView.findViewById(R.id.mark);
            rankLL = itemView.findViewById(R.id.rankLL);
            imgSuccess = itemView.findViewById(R.id.imgSuccess);

        }
    }
}
