package com.utkarshnew.android.testmodule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.utkarshnew.android.R;
import com.utkarshnew.android.testmodule.model.TestSection;

import java.util.ArrayList;
import java.util.List;

public class PartAdapter extends ArrayAdapter<TestSection> {

    ArrayList<TestSection> partLiast = new ArrayList<>();

    public PartAdapter(@NonNull Context context, ArrayList<TestSection> partLiast) {
        super(context, 0, (List<TestSection>) partLiast);
        this.partLiast = partLiast;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_part, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(partLiast.get(position).getName() + "-" + partLiast.get(position).getSection_title() +"( Pos Mark: " + partLiast.get(position).getMarksPerQuestion() + ": Neg Mark: " + partLiast.get(position).getNegativeMarks() + ")");
        return convertView;

    }
}
