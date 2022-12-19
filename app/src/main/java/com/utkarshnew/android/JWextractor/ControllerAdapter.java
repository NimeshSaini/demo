package com.utkarshnew.android.JWextractor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Player.Liveawsactivity;
import com.utkarshnew.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ControllerAdapter extends RecyclerView.Adapter<ControllerAdapter.ViewHolder> {
    Activity activity;
    private HashMap<Integer, UrlObject> popUpList;
    int oldpos = 0;
    SelectQuality selectQuality;

    public ControllerAdapter(Activity activity, HashMap<Integer, UrlObject> popUpList, SelectQuality selectQuality) {
        this.activity = activity;
        this.popUpList = popUpList;
        this.selectQuality = selectQuality;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton radio_speed;
        TextView spped_value;

        public ViewHolder(View v) {
            super(v);
            radio_speed = v.findViewById(R.id.radio_speed);
            spped_value = v.findViewById(R.id.spped_value);
        }
    }

    @NonNull
    @Override
    public ControllerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.speed_control_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ControllerAdapter.ViewHolder holder, final int position) {
        holder.spped_value.setText(Objects.requireNonNull(popUpList.get(position)).getTitle());
        if (Objects.requireNonNull(popUpList.get(position)).isSelected()) {
            oldpos = position;
            holder.radio_speed.setChecked(true);
        } else {
            holder.radio_speed.setChecked(false);
        }

        holder.radio_speed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (oldpos != position) {
                        if (activity instanceof JWVideoPlayer) {
                            if (((JWVideoPlayer) activity).player != null && ((JWVideoPlayer) activity).trackSelector != null && ((JWVideoPlayer) activity).trackSelector.getCurrentMappedTrackInfo() != null) {
                                Objects.requireNonNull(popUpList.get(oldpos)).setSelected(false);
                                notifyItemChanged(oldpos);
                                Objects.requireNonNull(popUpList.get(position)).setSelected(true);
                                notifyItemChanged(position);
                                selectQuality.selectQualityPos(position);
                            } else {

                                if (!Objects.requireNonNull(popUpList.get(position)).isSelected()) {
                                    holder.radio_speed.setChecked(false);
                                    Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                                }
                            }

                        } else if (activity instanceof Liveawsactivity) {
                            if (((Liveawsactivity) activity).player != null && ((Liveawsactivity) activity).trackSelector != null && ((Liveawsactivity) activity).trackSelector.getCurrentMappedTrackInfo() != null) {
                                Objects.requireNonNull(popUpList.get(oldpos)).setSelected(false);
                                notifyItemChanged(oldpos);
                                Objects.requireNonNull(popUpList.get(position)).setSelected(true);
                                notifyItemChanged(position);
                                selectQuality.selectQualityPos(position);
                            } else {

                                if (!Objects.requireNonNull(popUpList.get(position)).isSelected()) {
                                    holder.radio_speed.setChecked(false);
                                    Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                    }


                }
            }
        });

    /*
        holder.radio_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldpos!=position)
                {
                    selectQuality.selectQualityPos(position);
                }
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return popUpList.size();
    }
}
