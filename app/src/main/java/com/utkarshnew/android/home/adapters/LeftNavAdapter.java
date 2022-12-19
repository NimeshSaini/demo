package com.utkarshnew.android.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.model.Menu;
import com.utkarshnew.android.R;

import java.util.List;

public class LeftNavAdapter extends RecyclerView.Adapter<LeftNavAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTV;
        public ImageView iconIV, expandIV;
        public SwitchCompat switchCompat;
        RecyclerView recyclerView;
        LinearLayout feedsLL;

        public MyViewHolder(View view) {
            super(view);
            nameTV = (TextView) view.findViewById(R.id.nameTV);
            iconIV = (ImageView) view.findViewById(R.id.iconIV);
            switchCompat = (SwitchCompat) view.findViewById(R.id.switchCompat);
            expandIV = view.findViewById(R.id.expandIV);
            feedsLL = view.findViewById(R.id.feedsLL);

            recyclerView = view.findViewById(R.id.recyclerView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        try {
                            mListener.onItemClick(menuList.get(getAdapterPosition()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }


    public LeftNavAdapter(Context context, List<Menu> menuList) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_left_navigation, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Menu mMenu = menuList.get(position);
        holder.setIsRecyclable(false);

        holder.nameTV.setText(mMenu.getName());
        holder.iconIV.setImageResource(mMenu.getImage());

        if (mMenu.getHaveChild().equalsIgnoreCase("1")) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            final LeftNavAdapter adapter = new LeftNavAdapter(context, mMenu.getSubMenu());
            holder.recyclerView.setAdapter(adapter);

            if (mMenu.isExpanded()) {
                holder.expandIV.setBackground(context.getResources().getDrawable(R.drawable.ic_action_expand_less));
                holder.recyclerView.setVisibility(View.VISIBLE);
            } else {
                holder.expandIV.setBackground(context.getResources().getDrawable(R.drawable.ic_action_expand_more));
                holder.recyclerView.setVisibility(View.GONE);
            }

            adapter.setLeftNavAdapterListener(new LeftNavAdapterListener() {
                @Override
                public void onItemClick(Menu menu) {
                    if (menu.getHaveChild().equalsIgnoreCase("1")) {
                        if (menu.isExpanded()) {
                            menu.setExpanded(false);
                        } else {
                            menu.setExpanded(true);
                        }
                    }
                    ((HomeActivity) context).handleLeftMenuClick(menu);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    LeftNavAdapterListener mListener;

    public void setLeftNavAdapterListener(LeftNavAdapterListener listener) {
        this.mListener = listener;
    }

    public interface LeftNavAdapterListener {
        void onItemClick(Menu menu);
    }
}
