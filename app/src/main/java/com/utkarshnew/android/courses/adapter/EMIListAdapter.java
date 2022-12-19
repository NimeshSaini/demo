package com.utkarshnew.android.courses.adapter;

import static com.utkarshnew.android.Utils.Helper.getDaySuffix;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utkarshnew.android.Model.Courses.EMIInfo;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.HelperProgress;

import java.util.List;

public class EMIListAdapter extends BaseAdapter {

    private List<EMIInfo> filteredList;
    Context context;
    private static LayoutInflater inflater = null;

    public EMIListAdapter(Context context, List<EMIInfo> filteredList) {
        this.context = context;
        this.filteredList = filteredList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return filteredList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.item_emi, null);

        TextView title = (TextView) vi.findViewById(R.id.title);
        TextView desc = (TextView) vi.findViewById(R.id.desc);
        LinearLayout status_ll = (LinearLayout) vi.findViewById(R.id.status_ll);
        TextView text_status = (TextView) vi.findViewById(R.id.text_status);
        TextView next_payment_txt = (TextView) vi.findViewById(R.id.next_payment_txt);

        final EMIInfo emiInfo = filteredList.get(position);

        if (!TextUtils.isEmpty(emiInfo.getValidTo())) {
            status_ll.setVisibility(View.VISIBLE);
            next_payment_txt.setVisibility(View.VISIBLE);
            if (emiInfo.getTxnStatus().equalsIgnoreCase("1")) {
                text_status.setText("COMPLETE");
                text_status.setBackground(context.getResources().getDrawable(R.drawable.button_background_status_completed));
                String validDate = HelperProgress.getFormatDateMillis(Long.parseLong(emiInfo.getComletionDate()) * 1000);
                next_payment_txt.setTextColor(context.getResources().getColor(R.color.green_met));
                next_payment_txt.setText("Payment " + context.getResources().getString(R.string.rs) + " " + emiInfo.getEmiMrp() + "/- completed on " + validDate);
            } else {
                String validDate = HelperProgress.getFormatDateMillis(Long.parseLong(emiInfo.getValidTo()) * 1000);
                text_status.setText("PENDING");
                text_status.setBackground(context.getResources().getDrawable(R.drawable.button_background_status_pending));
                next_payment_txt.setTextColor(context.getResources().getColor(R.color.app_bg));
                next_payment_txt.setText("Next Payment " + context.getResources().getString(R.string.rs) + " " + emiInfo.getEmiMrp() + "/- due on " + validDate);
            }
        } else {
            status_ll.setVisibility(View.GONE);
            next_payment_txt.setVisibility(View.GONE);
        }

        title.setText(emiInfo.getEmiNo() + getDaySuffix(Integer.parseInt(emiInfo.getEmiNo())) + " Installment, Rs." + emiInfo.getEmiMrp() + "/ " + emiInfo.getEmiValidity() + "Days");
        desc.setText(emiInfo.getAttribute());
        return vi;
    }
}

/*
public class EMIListAdapter extends RecyclerView.Adapter<EMIListAdapter.ViewHolder> {

    private List<EMIInfo> filteredList;
    Context context;

    public EMIListAdapter(Context context, List<EMIInfo> filteredList) {
        this.context=context;
        this.filteredList=filteredList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final EMIInfo emiInfo = filteredList.get(position);
        holder.title.setText(emiInfo.getTitle());
        holder.desc.setText(emiInfo.getDescription());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView title;
        TextView desc;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
        }
    }

}
*/
