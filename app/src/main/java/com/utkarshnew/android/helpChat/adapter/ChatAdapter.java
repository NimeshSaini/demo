package com.utkarshnew.android.helpChat.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.helpChat.model.ChatQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<ChatQuery> chat_list;
    private int SENDER = 1;
    private int RECIEVER = 2;
    private Context context;

    public ChatAdapter(ArrayList<ChatQuery> chat_list) {
        this.chat_list = chat_list;
    }

    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp));
        return Helper.changeAMPM(sdf.format(currenTimeZone));
    }

    class SenderViewHolder extends ViewHolder {
        TextView sender_chat;
        TextView sender_chat_time;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sender_chat = itemView.findViewById(R.id.sender_chat);
            sender_chat_time = itemView.findViewById(R.id.sender_chat_time);

        }

        @Override
        void onBind(int position) {
            sender_chat.setText(chat_list.get(position).getText());
            if (TextUtils.isEmpty(chat_list.get(position).getCreateDate())) {
                sender_chat_time.setText("N/A");
            } else {
                try {
                    long timestamp_start = Long.parseLong(chat_list.get(position).getCreateDate()) * 1000;
//                    Calendar cal_start = Calendar.getInstance(Locale.ENGLISH);
//                    cal_start.setTimeInMillis(timestamp_start);
//                    Date date_start=cal_start.getTime();
//                    String dStart= java.text.DateFormat.getDateTimeInstance().format(date_start);

                    sender_chat_time.setText(getdate(String.valueOf(timestamp_start)));
                } catch (Exception e) {
                    sender_chat_time.setText("N/A");
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chat_list.get(position).getType().equalsIgnoreCase("1") ? SENDER : RECIEVER;
        //return RECIEVER;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return i == SENDER ? new SenderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_sender, viewGroup, false)) : new RecieverViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_reciever, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(i);
        /*if ((chat_list.size() - 1) == i)
            ((HelpChatActivity) context).quiry_id = chat_list.get(i).getQuery_id();*/
    }

    @Override
    public int getItemCount() {
        return chat_list.size();

    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        abstract void onBind(int position);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class RecieverViewHolder extends ViewHolder {
        TextView reciever_chat;
        TextView reciever_chat_time;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciever_chat = itemView.findViewById(R.id.reciever_chat);
            reciever_chat_time = itemView.findViewById(R.id.reciever_chat_time);
        }

        @Override
        void onBind(int position) {
            reciever_chat.setText(chat_list.get(position).getText());
            if (TextUtils.isEmpty(chat_list.get(position).getCreateDate())) {
                reciever_chat_time.setText("N/A");
            } else {
                try {
                    long timestamp_start = Long.parseLong(chat_list.get(position).getCreateDate()) * 1000;
//                    Calendar cal_start = Calendar.getInstance(Locale.ENGLISH);
//                    cal_start.setTimeInMillis(timestamp_start);
//                    Date date_start=cal_start.getTime();
//                    String dStart= java.text.DateFormat.getDateTimeInstance().format(date_start);
                    reciever_chat_time.setText(getdate(String.valueOf(timestamp_start)));

                } catch (Exception e) {
                    reciever_chat_time.setText("N/A");
                }
            }

        }
    }
}