package com.utkarshnew.android.helpChat.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.helpChat.helper.OnQueryItemListener;
import com.utkarshnew.android.helpChat.model.HelpSupportChatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HelpQueryRecyclerAdapter extends RecyclerView.Adapter<HelpQueryRecyclerAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private OnQueryItemListener mListener;
    private ArrayList<HelpSupportChatModel.DataBean> data;
    Context context;

    public HelpQueryRecyclerAdapter(Context context, ArrayList<HelpSupportChatModel.DataBean> items) {
        data = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.help_query_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HelpSupportChatModel.DataBean postResponse = data.get(position);

        holder.queryIDTV.setText(TextUtils.isEmpty(postResponse.getTime()) ? "N/A" : postResponse.getQuery_id());
        holder.queryTextTV.setText(TextUtils.isEmpty(postResponse.getTime()) ? "N/A" : postResponse.getTitle());

        if (postResponse.getClose_date().equalsIgnoreCase("0")) {
            String openStr = "Status: <font color='black'><b>" + "Open" + "</b></font>";
            holder.querystatusTV.setText(Html.fromHtml(openStr));
        } else {
            String closeStr = "Status: <font color='green'><b>" + "Closed" + "</b></font>";
            holder.querystatusTV.setText(Html.fromHtml(closeStr));
        }

        if (TextUtils.isEmpty(postResponse.getTime())) {
            holder.queryTimeTV.setText("N/A");
        } else {
            try {
                long timestamp_start = Long.parseLong(postResponse.getTime()) * 1000;

            /*    Calendar cal_start = Calendar.getInstance(Locale.ENGLISH);
                cal_start.setTimeInMillis(timestamp_start);
                Date date_start=cal_start.getTime();
                String dStart= java.text.DateFormat.getDateTimeInstance().format(date_start);
            */
                holder.queryTimeTV.setText(getdate(String.valueOf(timestamp_start)));
            } catch (Exception e) {
                holder.queryTimeTV.setText("N/A");
            }
        }

        holder.queryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener = (OnQueryItemListener) context;
                if (null != mListener) {
                    mListener.onQueryItemClick(postResponse);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        RelativeLayout queryLL;
        TextView queryIDTV;
        TextView queryTextTV;
        TextView querystatusTV;
        TextView queryTimeTV;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            queryLL = itemView.findViewById(R.id.queryLL);
            queryIDTV = itemView.findViewById(R.id.queryIDTV);
            queryTextTV = itemView.findViewById(R.id.queryTextTV);
            querystatusTV = itemView.findViewById(R.id.querystatusTV);
            queryTimeTV = itemView.findViewById(R.id.queryTimeTV);

        }
    }

    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp));
        return Helper.changeAMPM(sdf.format(currenTimeZone));
    }

}
