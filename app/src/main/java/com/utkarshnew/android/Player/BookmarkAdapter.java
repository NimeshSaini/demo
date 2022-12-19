package com.utkarshnew.android.Player;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.utkarshnew.android.JWextractor.JWVideoPlayer;
import com.utkarshnew.android.Model.PlayerPojo.VideoTimeFramePojo;
import com.utkarshnew.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public int position;
    List<VideoTimeFramePojo> data;
    Activity activity;
    String fromwhere;

    public BookmarkAdapter(Activity activity, List<VideoTimeFramePojo> data, String fromwhere) {
        this.data = data;
        this.activity = activity;
        this.fromwhere = fromwhere;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.bookmark_index_row, null);
        BookmarkAdapter.MyViewHolder viewholder = new BookmarkAdapter.MyViewHolder(view);
        return viewholder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder /*implements SingleFeedView.DeletePostCallback*/ {

        TextView timeTV, tittleTV;
        ImageView deleteIV;

        public MyViewHolder(View itemView) {
            super(itemView);
            deleteIV = itemView.findViewById(R.id.del_iv);
            timeTV = itemView.findViewById(R.id.vediotime);
            tittleTV = itemView.findViewById(R.id.vediotitle);
            tittleTV.setSelected(true);


            deleteIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alert_dialog(getAdapterPosition());

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date = null;
                    try {
                        date = sdf.parse("1970-01-01 " + data.get(getAdapterPosition()).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date.getTime();
                    if (fromwhere.equalsIgnoreCase("jwplayer")) {
                        ((JWVideoPlayer) activity).setVideoTimeMS((int) date.getTime());
                    } else if (fromwhere.equalsIgnoreCase("Liveaws")) {
                        if (activity instanceof DrmVideoPlayerActivity)
                        {
                            ((DrmVideoPlayerActivity) activity).setVideoTimeMS((int) date.getTime());
                        }else {
                            ((Liveawsactivity) activity).setVideoTimeMS((int) date.getTime());
                        }
                    } else {
                        ((CustomMediaPlayer) activity).setVideoTimeMS((int) date.getTime());
                    }
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder sholder, final int position) {
        MyViewHolder holder = (MyViewHolder) sholder;
        holder.timeTV.setText(data.get(position).getTime());
        holder.tittleTV.setText(data.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void alert_dialog(int adapterPosition) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Delete Bookmark");
        alertDialogBuilder.setMessage("Are you sure,You want to delete the Bookmark ?");
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {

            if (fromwhere.equalsIgnoreCase("jwplayer")) {
                ((JWVideoPlayer) activity).onDelete(data.get(adapterPosition), adapterPosition);
            } else if (fromwhere.equalsIgnoreCase("Liveaws")) {
                if(activity instanceof DrmVideoPlayerActivity)
                {
                    ((DrmVideoPlayerActivity) activity).onDelete(data.get(adapterPosition), adapterPosition);

                }else if (activity instanceof  Liveawsactivity)
                {
                    ((Liveawsactivity) activity).onDelete(data.get(adapterPosition), adapterPosition);

                }
            } else {
                ((CustomMediaPlayer) activity).onDelete(data.get(adapterPosition), adapterPosition);
            }

            dialog.dismiss();
            dialog.cancel();
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
