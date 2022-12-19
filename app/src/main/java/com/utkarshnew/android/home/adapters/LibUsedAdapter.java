package com.utkarshnew.android.home.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utkarshnew.android.home.model.LibUsedModel;
import com.utkarshnew.android.R;


/**
 * Created by root on 17/3/18 7:58 PM.
 */

public class LibUsedAdapter extends RecyclerView.Adapter<LibUsedAdapter.ViewHolder> {

    private Context mContext;
    private String[] sectionTitles = new LibUsedModel().getLibName(), sectionDesc = new LibUsedModel().getLibDesc();

    public LibUsedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mSectionName, mSectionDesc;

        public ViewHolder(View v) {
            super(v);
            mSectionName = v.findViewById(R.id.libnametext);
            mSectionDesc = v.findViewById(R.id.libdesctext);
        }

    }

    @Override
    public LibUsedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.libusedlistitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LibUsedAdapter.ViewHolder holder, int position) {
        holder.mSectionName.setText(sectionTitles[position]);

        if (sectionDesc.length != position && !(sectionDesc.length < position)) {
            holder.mSectionDesc.setText(sectionDesc[position]);
        }
    }

    @Override
    public int getItemCount() {
        return sectionTitles.length;
    }
}
