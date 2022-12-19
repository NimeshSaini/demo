package com.utkarshnew.android.testmodule.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.testmodule.model.Social;

import java.util.ArrayList;

public class FIB_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int items;
    ArrayList tags = new ArrayList();
    private Activity ctx;
    private OnItemClickListener mOnItemClickListener;

    int pagerposition;


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Social obj, int bg, int circle, boolean select);
    }

    public FIB_Adapter(Activity context, int position, ArrayList tags) {

        this.tags = tags;

        this.pagerposition = position;
        ctx = context;
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public LinearLayout viewLL;
        public TextView optionIconTV;
        public EditText optionEditTV;
        public ImageButton bt_move;


        public OriginalViewHolder(View v) {
            super(v);

            //optionEditTV = (EditText) v.findViewById(R.id.optionEditTV);
            viewLL = (LinearLayout) v.findViewById(R.id.viewLL);
            optionIconTV = (TextView) v.findViewById(R.id.optionIconTV);
            //  bt_move = (ImageButton) v.findViewById(R.id.bt_move);

        }

      /*  @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ctx.getResources().getColor(R.color.colorGray3));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }*/
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_option_test_view_fib, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;
//
//         view.optionEditTV.setHint("Enter answer");
//         view.optionEditTV.setText(tags.get(position).toString());
//            view.optionEditTV.addTextChangedListener(new GenericTextWatcher(view.optionEditTV, position));
//          /*
//            // Start a drag whenever the handle view it touched
//            view.lyt_parent.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN && mDragStartListener != null) {
//                        mDragStartListener.onStartDrag(holder);
//                    }
//                    return false;
//                }
//            });*/

        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(items, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }

    private class GenericTextWatcher implements TextWatcher {
        private View view;
        private int tag;

        private GenericTextWatcher(View view, int tag) {
            this.view = view;
            this.tag = tag;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            tags.remove(tag);
            tags.add(tag, text);

           /* for (int i = 0; i < tags.size(); i++) {
                if (!tags.get(i).toString().equalsIgnoreCase("")) {
                    ((TestBaseActivity)ctx).questionBankList.get(pagerposition).setIsanswer(true, 1, tags);
                    break;
                } else {
                    ((TestBaseActivity)ctx).questionBankList.get(pagerposition).setIsanswer(false, 0,tags);
                }
            }*/
        }
    }
}