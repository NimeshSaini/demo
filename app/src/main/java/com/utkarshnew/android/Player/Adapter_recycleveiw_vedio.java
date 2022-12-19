package com.utkarshnew.android.Player;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.utkarshnew.android.JWextractor.JWVideoPlayer;
import com.utkarshnew.android.Model.PlayerPojo.VideoTimeFramePojo;
import com.utkarshnew.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Admin on 16-03-2019.
 */

public class Adapter_recycleveiw_vedio extends RecyclerView.Adapter<Adapter_recycleveiw_vedio.Viewholder> {
    private Activity con;

    private List<VideoTimeFramePojo> product;
    private String fromwhere;

    public Adapter_recycleveiw_vedio(Activity con, List<VideoTimeFramePojo> product, String fromwhere) {
        this.con = con;
        this.product = product;
        this.fromwhere = fromwhere;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        View view = layoutInflater.inflate(R.layout.activity_cardview_vedio, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final VideoTimeFramePojo videoTimeFramePojo = product.get(position);
        holder.text.setText(videoTimeFramePojo.getTime().trim());
        holder.text2.setText(videoTimeFramePojo.getInfo().trim());
        holder.parentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = null;
                try {
                    date = sdf.parse("1970-01-01 " + videoTimeFramePojo.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                date.getTime();
                if (fromwhere.equalsIgnoreCase("custom")) {
                    ((CustomMediaPlayer) con).setVideoTimeMS((int) date.getTime());
                } else if (fromwhere.equalsIgnoreCase("jw")) {
                    ((JWVideoPlayer) con).setVideoTimeMS((int) date.getTime());
                } else {
                    ((Liveawsactivity) con).setVideoTimeMS((int) date.getTime());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView text, text2;
        private CardView parentCV;

        public Viewholder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.vediotime);
            text2 = itemView.findViewById(R.id.vediotitle);
            parentCV = itemView.findViewById(R.id.parentCV);
        }
    }
}
