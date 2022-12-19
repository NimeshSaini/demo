package com.utkarshnew.android.Login.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.Login.Activity.LoginCatActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Response.Registration.SubStreamResponse;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;

/**
 * Created by appsquadz on 4/16/2018.
 */

public class SubCategoryExamsAdapter extends RecyclerView.Adapter<SubCategoryExamsAdapter.ViewHolder> {

    Context context;
    ArrayList<SubStreamResponse> specializationResponseArrayList;

    public SubCategoryExamsAdapter(Context activity, ArrayList<SubStreamResponse> specializationResponseArrayList) {
        this.context = activity;
        this.specializationResponseArrayList = specializationResponseArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exams_subcategory_selection_ver, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        // todo need to change when the dynamic URL come some the API
//        Ion.with(context).load(examCategoryArrayList.get(position).getImage_url()).asBitmap().setCallback(new FutureCallback<Bitmap>() {
//            @Override
//            public void onCompleted(Exception e, Bitmap result) {
//                holder.imageView.setImageBitmap(result);
//            }
//        });
        holder.title.setText(specializationResponseArrayList.get(position).getText_name());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bank_exams));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference.getInstance().putString(Const.SUB_CAT, specializationResponseArrayList.get(position).getId());
                Intent intent = new Intent(context, LoginCatActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.INTRO);
                intent.putExtra(Const.SUB_CAT, specializationResponseArrayList.get(position).getText_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return specializationResponseArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView subTitle;
        TextView title;
        LinearLayout parent;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.examsCategoryIV);
            title = itemView.findViewById(R.id.examsCategoryTitleTV);
            subTitle = itemView.findViewById(R.id.examsCategorySubtitleTV);
            parent = itemView.findViewById(R.id.examsCategoryMainLL);
        }
    }
}
