package com.utkarshnew.android.testmodule.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.NonScrollRecyclerView;
import com.utkarshnew.android.testmodule.interfaces.MachingOnDrag;
import com.utkarshnew.android.testmodule.model.TestSections;
import com.utkarshnew.android.testmodule.model.TopTenList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {
    Context context;
    List<TestSections> sectionsArrayList = new ArrayList<>();
    int oldppos =0;

    MachingOnDrag machingOnDrag;
    public SectionAdapter(Activity activity, ArrayList<TestSections> sectionsArrayList, MachingOnDrag machingOnDrag) {
        this.context = activity;

        sectionsArrayList.get(0).setSelected_section( true);
        this.sectionsArrayList = sectionsArrayList;
        this.machingOnDrag = machingOnDrag;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position ==oldppos)
        {
            holder.section_button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.result_part_bg_active));
        }else {
            sectionsArrayList.get(position).setSelected_section(false);
            holder.section_button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.result_part_bg_deactive));
        }
        holder.section_button.setText(sectionsArrayList.get(position).getName() +"-"+sectionsArrayList.get(position).getSection_title()+"( Pos Mark: " + sectionsArrayList.get(position).getMarksPerQuestion() + ": Neg Mark: " + sectionsArrayList.get(position).getNegativeMarks() + ")");


        holder.section_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (machingOnDrag!=null)
                {
                    int copy_lastpos =oldppos;
                    oldppos = position;
                    notifyItemChanged(copy_lastpos);
                    notifyItemChanged(oldppos);
                    machingOnDrag.sendOnclickInd(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionsArrayList.size();
    }


    public class MyViewHolder extends NonScrollRecyclerView.ViewHolder {

        Button section_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            section_button = itemView.findViewById(R.id.btnPartA);

        }
    }
}
