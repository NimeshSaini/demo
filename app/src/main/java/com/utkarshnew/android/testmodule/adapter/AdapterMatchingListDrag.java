package com.utkarshnew.android.testmodule.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.interfaces.MachingOnDrag;
import com.utkarshnew.android.testmodule.model.Social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterMatchingListDrag extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DragItemTouchHelper.MoveHelperAdapter, DragItemTouchHelper.SwipedHelperAdapter, MachingOnDrag {

    private List<Social> items = new ArrayList<>();

    private Activity ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnStartDragListener mDragStartListener = null;
    MachingOnDrag machingOnDrag;
    int pagerposition;


    public AdapterMatchingListDrag(Activity context, List<Social> items, int position) {
        this.items = items;

        this.pagerposition = position;

        ctx = context;
    }

    @Override
    public void sendOnclickInd(int position) {

    }

    public interface OnItemClickListener {
        void onItemClick(View view, Social obj, int position, int bg, int circle, boolean select);
    }

    public interface MachingOnDrag {
        void sendOnclickInd(int position);
    }

    public void setMachingOnDrag(MachingOnDrag machingOnDrag) {
        this.machingOnDrag = machingOnDrag;
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public void setDragListener(OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements DragItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView name;
        public LinearLayout llmain;
        public TextView optionIconTV;
        public ImageButton bt_move;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            llmain = (LinearLayout) v.findViewById(R.id.llmain);
            optionIconTV = (TextView) v.findViewById(R.id.optionIconTV);
            //  bt_move = (ImageButton) v.findViewById(R.id.bt_move);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ctx.getResources().getColor(R.color.light_quiz_grey));
        }

        @Override
        public void onItemClear(int toPosition) {
            itemView.setBackgroundColor(0);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Social p = items.get(position);
            view.name.setText(p.name);
            view.optionIconTV.setText(p.option);


            if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size() != 0 && ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted() != null) {
                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size(); i++) {
                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getPosition() == position) {

                        if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).isSelect()) {
                            ((OriginalViewHolder) holder).llmain.setSelected(true);
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                            final int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getBgcolor_code()));
                                ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getCirclecolor_code()));
                            } else {
                                ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getBgcolor_code()));
                                ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getCirclecolor_code()));
                            }

                        } else {
                            ((OriginalViewHolder) holder).llmain.setSelected(false);
                            ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                            ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));
                        }
                        break;
                    } else {
                        ((OriginalViewHolder) holder).llmain.setSelected(false);
                        ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                        ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                        ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));
                    }
                }
            } else if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted() != null) {
                ((OriginalViewHolder) holder).llmain.setSelected(false);
                ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));
            }
//            List<String> list= Arrays.asList(p.getSelcted().split("\\s*,\\s*"));
//            for (int i = 0; i <list.size() ; i++) {
//                if (!list.get(i).equalsIgnoreCase("-1")){
//                    view.llmain.setSelected(true);
//                }
//            }
            //   Tools.displayImageOriginal(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        boolean isSelected = false;
                        int selectedPosition = 0;

                        if (((OriginalViewHolder) holder).llmain.isSelected()) {
                            if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size() != 0 && ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted() != null) {

                                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size(); i++) {
                                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getPosition() == position) {

                                        if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).isSelect()) {
                                            for (int j = 0; j < ((TestBaseActivity) ctx).SAMPLE_CIRCLE.length; j++) {
                                                if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getCirclecolor_code() == ((TestBaseActivity) ctx).SAMPLE_CIRCLE[j]) {
                                                    machingOnDrag.sendOnclickInd(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                           /* for (int j = 0; j < ((TestBaseActivity)ctx).questionBankList.get(pagerposition).getSelcted2().size(); j++) {
                                for (int i = 0; i < ((TestBaseActivity) ctx).SAMPLE_CIRCLE.length; i++) {
                                   if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(j).getCirclecolor_code() == ((TestBaseActivity) ctx).SAMPLE_CIRCLE[i]) {
                                       machingOnDrag.sendOnclickInd(i);
                                       break;
                                   }
                                }
//                                if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(j).isSelect()) {
//                                    isSelected = true;
//                                    selectedPosition=((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(j).getPosition();
//                                   // selectedPosition=((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(j).getCirclecolor_code();
//                                }
                            }*/
                           /* if (isSelected) {
                                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size(); i++) {
                                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).isSelect()) {
                                        for (int j = 0; j < ((TestBaseActivity) ctx).SAMPLE_CIRCLE.length; j++) {
                                            if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(position).getCirclecolor_code()== ((TestBaseActivity) ctx).SAMPLE_CIRCLE[j]){
                                                machingOnDrag.sendOnclickInd(j);
                                              // SC_TestSeriesFragment.setNotifyNormalAdapter(j);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }*/


                            ((OriginalViewHolder) holder).llmain.setSelected(false);
                            ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                            ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));

                            mOnItemClickListener.onItemClick(view, items.get(position), position, TestBaseActivity.nestbg, TestBaseActivity.nestcirclebg, false);
                        } else if (TestBaseActivity.nestselected) {
                            ((OriginalViewHolder) holder).llmain.setSelected(true);
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                            final int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                            } else {
                                ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                            }
                            TestBaseActivity.nestselected = false;
                            TestBaseActivity.matchingPositiondrag = position;
                            mOnItemClickListener.onItemClick(view, items.get(position), position, TestBaseActivity.nestbg, TestBaseActivity.nestcirclebg, true);

                        } else {
                            if (TestBaseActivity.matchingPositiondrag != -1) {
                                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size(); i++) {
                                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(i).getPosition() == TestBaseActivity.matchingPositiondrag) {
                                        ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().remove(i);
                                        break;
                                    }
                                }
                                ((OriginalViewHolder) holder).llmain.setSelected(true);
                                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                                final int sdk = android.os.Build.VERSION.SDK_INT;
                                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                                } else {
                                    ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                                }
                                TestBaseActivity.nestselected = false;
                                TestBaseActivity.matchingPositiondrag = position;
                                mOnItemClickListener.onItemClick(view, items.get(position), position, TestBaseActivity.nestbg, TestBaseActivity.nestcirclebg, true);
                                notifyDataSetChanged();
                            } else if (TestBaseActivity.sameselected) {
                                ((OriginalViewHolder) holder).llmain.setSelected(true);
                                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                                final int sdk = android.os.Build.VERSION.SDK_INT;
                                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                                } else {
                                    ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestbg));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.nestcirclebg));
                                }
                                TestBaseActivity.nestselected = false;
                                TestBaseActivity.matchingPositiondrag = position;
                                mOnItemClickListener.onItemClick(view, items.get(position), position, TestBaseActivity.nestbg, TestBaseActivity.nestcirclebg, true);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            });


          /*  // Start a drag whenever the handle view it touched
            view.lyt_parent.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN && mDragStartListener != null) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });*/

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        //  machingOnDrag.sendOnclickInd(fromPosition,toPosition);

        return true;
    }

    @Override
    public boolean onItemSwiped(int toPosition) {

        return true;
    }


}