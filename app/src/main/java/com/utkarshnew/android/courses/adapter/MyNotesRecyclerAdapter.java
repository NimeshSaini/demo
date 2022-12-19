package com.utkarshnew.android.courses.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.courses.Interfaces.OnMyNotesItemListener;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;
import com.utkarshnew.android.R;

import java.util.ArrayList;

public class MyNotesRecyclerAdapter extends RecyclerView.Adapter<MyNotesRecyclerAdapter.ViewHolder> {

    private ArrayList<NoteData> filteredList;
    Context context;
    private OnMyNotesItemListener mListener;

    public MyNotesRecyclerAdapter(Context context, ArrayList<NoteData> filteredList) {
        this.context = context;
        this.filteredList = filteredList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_notes_item_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final NoteData noteData = filteredList.get(position);

        if (noteData.getType().equalsIgnoreCase("note")) {
            holder.edit_btn.setVisibility(View.GONE);
            holder.delete_btn.setVisibility(View.GONE);
        } else {
            holder.edit_btn.setVisibility(View.GONE);
            holder.delete_btn.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(noteData.getTitle())) {
            holder.notes_title.setVisibility(View.VISIBLE);
            holder.notes_title.setText(noteData.getTitle());
        } else {
            holder.notes_title.setVisibility(View.GONE);
        }
        holder.notes_desc.setText(noteData.getQueryData());

        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener = (OnMyNotesItemListener) context;
                if (null != mListener) {
                    mListener.onMyNotesEditClick(noteData, position);
                }
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener = (OnMyNotesItemListener) context;
                if (null != mListener) {
                    mListener.onMyNotesDeleteClick(noteData, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView notes_title;
        TextView notes_desc;
        CardView edit_btn, delete_btn;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            notes_title = itemView.findViewById(R.id.notes_title);
            notes_desc = itemView.findViewById(R.id.notes_desc);

        }
    }

}
