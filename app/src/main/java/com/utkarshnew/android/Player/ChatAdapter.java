package com.utkarshnew.android.Player;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.utkarshnew.android.Model.chatPojo;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {

    public ArrayList<chatPojo> dataSet;
    Context mContext;
    String type;
    private static final String TAG = "LiveAdapter";

    public ChatAdapter(Context context, String type, ArrayList<chatPojo> dataSet) {
        this.mContext = context;
        this.dataSet = dataSet;
        this.type = type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int listPosition) {

        int itemtype = getItemViewType(listPosition);
        if (itemtype == 1) {
            final MyViewHolder2 holder = (MyViewHolder2) holder1;
            setTransparentData(holder, listPosition);
        } else {
            final MyViewHolder holder = (MyViewHolder) holder1;
            setData(holder, listPosition);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_trans_chat_layout, parent, false);
            return new MyViewHolder2(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_chat_layout, parent, false);
            return new MyViewHolder(view);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cvrLeft, cvrLeftimage, cvrRight, cvrRightimage;
        CircleImageView profileImage, profileImageimage, profileImage2, profileImage2image;
        TextView userName, userNameright, rightmessage, letfmessageTv;
        TextView userNameimage, userNamerightimage, lefttexttime, leftimagetime, righttexttime, rightimagettime;
        ImageView rightmessageimage, letfmessageTvimage;

        public MyViewHolder(View itemView) {
            super(itemView);
            cvrLeft = itemView.findViewById(R.id.cvrLeft);
            cvrLeftimage = itemView.findViewById(R.id.cvrLeftimage);
            cvrRight = itemView.findViewById(R.id.cvrRight);
            cvrRightimage = itemView.findViewById(R.id.cvrRightimage);
            /*Left Side start*/
            profileImage = itemView.findViewById(R.id.profileImage);
            profileImageimage = itemView.findViewById(R.id.profileImageimage);
            userName = itemView.findViewById(R.id.userName);
            userNameimage = itemView.findViewById(R.id.userNameimage);
            letfmessageTv = itemView.findViewById(R.id.letfmessageTv);
            letfmessageTvimage = itemView.findViewById(R.id.letfmessageTvimage);
            lefttexttime = itemView.findViewById(R.id.lefttexttime);
            leftimagetime = itemView.findViewById(R.id.leftimagetime);
            righttexttime = itemView.findViewById(R.id.righttexttime);
            rightimagettime = itemView.findViewById(R.id.rightimagettime);

            /*Left Side End*/
            /*Right SIde*/
            profileImage2 = itemView.findViewById(R.id.profileImage2);
            profileImage2image = itemView.findViewById(R.id.profileImage2image);
            userNameright = itemView.findViewById(R.id.userNameright);
            userNamerightimage = itemView.findViewById(R.id.userNamerightimage);
            rightmessage = itemView.findViewById(R.id.rightmessage);
            rightmessageimage = itemView.findViewById(R.id.rightmessageimage);
        }
    }

    public void setData(final MyViewHolder holder, int listPosition) {
        if (mContext instanceof Liveawsactivity) {
            ((Liveawsactivity) mContext).ischatload = true;
        }else
        if (mContext instanceof DrmVideoPlayerActivity) {
            ((DrmVideoPlayerActivity) mContext).ischatload = true;
        }else {
            ((CustomMediaPlayer) mContext).ischatload = true;
        }
        holder.cvrLeft.setVisibility(View.GONE);
        holder.cvrLeftimage.setVisibility(View.GONE);
        holder.cvrRight.setVisibility(View.GONE);
        holder.cvrRightimage.setVisibility(View.GONE);
        if (dataSet.get(listPosition).getId().equals(SharedPreference.getInstance().getLoggedInUser().getId())) {

            if (dataSet.get(listPosition).getType().equalsIgnoreCase("text")) {
                holder.cvrRight.setVisibility(View.VISIBLE);
                holder.rightmessage.setText(dataSet.get(listPosition).getMessage().trim());
                holder.userNameright.setText(dataSet.get(listPosition).getName().trim());
                holder.righttexttime.setText(getdate(String.valueOf(dataSet.get(listPosition).getDate())));
                Glide.with(mContext).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.profileImage2);
//                Glide.with(mContext).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
//                        .into(holder.profileImage);
            } else {
                holder.cvrRightimage.setVisibility(View.VISIBLE);

                Glide.with(mContext.getApplicationContext()).load(dataSet.get(listPosition).getMessage().trim()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.rightmessageimage);
                holder.userNamerightimage.setText(dataSet.get(listPosition).getName().trim());
                holder.rightimagettime.setText(getdate(String.valueOf(dataSet.get(listPosition).getDate())));
                Glide.with(mContext).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.profileImage2image);
            }


        } else {

            if (dataSet.get(listPosition).getType().equalsIgnoreCase("text")) {
                holder.cvrLeft.setVisibility(View.VISIBLE);
                holder.letfmessageTv.setText(dataSet.get(listPosition).getMessage().trim());
                holder.userName.setText(dataSet.get(listPosition).getName().trim());
                holder.lefttexttime.setText(getdate(String.valueOf(dataSet.get(listPosition).getDate())));
                Glide.with(mContext).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.profileImage);
            } else {
                holder.cvrLeftimage.setVisibility(View.VISIBLE);
                Glide.with(mContext.getApplicationContext()).load(dataSet.get(listPosition).getMessage().trim()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.letfmessageTvimage);
                holder.userNameimage.setText(dataSet.get(listPosition).getName().trim());
                Glide.with(mContext).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                        .into(holder.profileImageimage);
                holder.leftimagetime.setText(getdate(String.valueOf(dataSet.get(listPosition).getDate())));

            }


        }
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        ImageView ivAdmin;
        TextView tvAdmin, tv_username, tv_time;
        LinearLayout llAdmin;

        public MyViewHolder2(View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            tv_time = itemView.findViewById(R.id.tv_time);

            ivAdmin = itemView.findViewById(R.id.iv_admin);

            tvAdmin = itemView.findViewById(R.id.tv_admin);

            llAdmin = itemView.findViewById(R.id.ll_admin);
        }
    }

    private void setTransparentData(final MyViewHolder2 holder, int listPosition) {
        if (!dataSet.get(listPosition).getProfile_picture().equals("")) {
            /*Ion.with(mContext)
                    .load(dataSet.get(listPosition).getProfile_picture())
                    .asBitmap().setCallback(new FutureCallback<Bitmap>() {
                @Override
                public void onCompleted(Exception e, Bitmap result) {
                    if (e == null)
                        holder.ivAdmin.setImageBitmap(result);
                    else
                        holder.ivAdmin.setImageResource(R.drawable.profile_grey);
                }
            });*/

            Glide.with(mContext.getApplicationContext()).load(dataSet.get(listPosition).getProfile_picture()).apply(new RequestOptions().placeholder(R.drawable.profile_grey))
                    .into(holder.ivAdmin);
        } else {
            holder.ivAdmin.setImageResource(R.drawable.profile_grey);
        }
        holder.llAdmin.setVisibility(View.VISIBLE);
        holder.tvAdmin.setText(dataSet.get(listPosition).getMessage());
        holder.tv_username.setText(dataSet.get(listPosition).getName());
        holder.tv_time.setText(String.valueOf(dataSet.get(listPosition).getDate()));
        /*try {
            holder.tv_time.setText(new SimpleDateFormat("dd MMM,yyyy hh:mm a").format(new Date( Long.parseLong(dataSet.get(listPosition).getDate()))));
        }catch (Exception e){
            holder.tv_time.setText(dataSet.get(listPosition).getDate());
        }*/

    }

    @Override
    public int getItemViewType(int position) {
        if (type.equals("trans")) return 1; // list of transparent layout
        else return 0;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
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
