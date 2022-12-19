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
import com.utkarshnew.android.testmodule.model.Social;

import java.util.ArrayList;
import java.util.List;

public class AdapterMatchingListNormal extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Social> items = new ArrayList<>();

    private Activity ctx;
    private OnItemClickListener mOnItemClickListener;
    int[] androidColors;
    int pagerposition;
    MachingOnDrag machingOnDrag;


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Social obj, int bg, int circle, boolean select);
    }

    public AdapterMatchingListNormal(Activity context, List<Social> items, int[] androidColors, int position) {
        this.items = items;
        this.androidColors = androidColors;
        this.pagerposition = position;
        ctx = context;
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface MachingOnDrag {
        void sendOnclickInd(int position);
    }

    public void setMachingOnDrag(MachingOnDrag machingOnDrag) {
        this.machingOnDrag = machingOnDrag;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder {
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
            if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().size() != 0 && ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2() != null) {
                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().size(); i++) {
                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getPosition() == position) {
                        if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).isSelect()) {
                            ((OriginalViewHolder) holder).llmain.setSelected(true);
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                            final int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getBgcolor_code()));
                                ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getCirclecolor_code()));
                            } else {
                                ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getBgcolor_code()));
                                ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getCirclecolor_code()));
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
            } else if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2() != null) {
                ((OriginalViewHolder) holder).llmain.setSelected(false);
                ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));
            }
/*
               List<String> list= Arrays.asList(p.getSelcted2().split("\\s*,\\s*"));
                for (int i = 0; i <list.size() ; i++) {
                    if (!list.get(i).equalsIgnoreCase("-1")){
                        view.llmain.setSelected(true);
                    }
                }*/

            //   Tools.displayImageOriginal(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        if (((OriginalViewHolder) holder).llmain.isSelected()) {
                            if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().size() != 0 && ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2() != null) {

                                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().size(); i++) {
                                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getPosition() == position) {

                                        if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).isSelect()) {
                                            for (int j = 0; j < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().size(); j++) {
                                                if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted().get(j).getCirclecolor_code() == ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getCirclecolor_code()) {
                                                    machingOnDrag.sendOnclickInd(j);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                            ((OriginalViewHolder) holder).llmain.setSelected(false);
                            ((OriginalViewHolder) holder).llmain.setBackground(ctx.getResources().getDrawable(R.drawable.bg_mcq_unselected));
                            ((OriginalViewHolder) holder).optionIconTV.setBackground(ctx.getResources().getDrawable(R.drawable.circle_unselect));
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.grey_80));
                            mOnItemClickListener.onItemClick(view, position, items.get(position), TestBaseActivity.SAMPLE_BG[position], TestBaseActivity.SAMPLE_CIRCLE[position], false);
                        } else if (!TestBaseActivity.nestselected) {
                            ((OriginalViewHolder) holder).llmain.setSelected(true);
                            ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                            final int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                            } else {
                                ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                            }
                            TestBaseActivity.nestcirclebg = TestBaseActivity.SAMPLE_CIRCLE[position];
                            TestBaseActivity.nestbg = TestBaseActivity.SAMPLE_BG[position];
                            TestBaseActivity.nestselected = true;
                            TestBaseActivity.matchingPosition = position;
                            mOnItemClickListener.onItemClick(view, position, items.get(position), TestBaseActivity.SAMPLE_BG[position], TestBaseActivity.SAMPLE_CIRCLE[position], true);
                        } else {
                            if (TestBaseActivity.matchingPosition != -1) {
                                for (int i = 0; i < ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().size(); i++) {
                                    if (((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().get(i).getPosition() == TestBaseActivity.matchingPosition) {
                                        ((TestBaseActivity) ctx).questionBankList.get(pagerposition).getSelcted2().remove(i);
                                        break;
                                    }

                                }
                                ((OriginalViewHolder) holder).llmain.setSelected(true);
                                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                                final int sdk = android.os.Build.VERSION.SDK_INT;
                                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                                } else {
                                    ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                                }
                                TestBaseActivity.nestcirclebg = TestBaseActivity.SAMPLE_CIRCLE[position];
                                TestBaseActivity.nestbg = TestBaseActivity.SAMPLE_BG[position];
                                TestBaseActivity.nestselected = true;
                                TestBaseActivity.matchingPosition = position;


                                mOnItemClickListener.onItemClick(view, position, items.get(position), TestBaseActivity.SAMPLE_BG[position], TestBaseActivity.SAMPLE_CIRCLE[position], true);

                                notifyDataSetChanged();
                            } else {
                                ((OriginalViewHolder) holder).llmain.setSelected(true);
                                ((OriginalViewHolder) holder).optionIconTV.setTextColor(ctx.getResources().getColor(R.color.white));
                                final int sdk = android.os.Build.VERSION.SDK_INT;
                                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    ((OriginalViewHolder) holder).llmain.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackgroundDrawable(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                                } else {
                                    ((OriginalViewHolder) holder).llmain.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_BG[position]));
                                    ((OriginalViewHolder) holder).optionIconTV.setBackground(ContextCompat.getDrawable(ctx, TestBaseActivity.SAMPLE_CIRCLE[position]));
                                }
                                TestBaseActivity.nestcirclebg = TestBaseActivity.SAMPLE_CIRCLE[position];
                                TestBaseActivity.nestbg = TestBaseActivity.SAMPLE_BG[position];
                                TestBaseActivity.nestselected = true;
                                TestBaseActivity.sameselected = true;
                                TestBaseActivity.matchingPosition = position;
                                mOnItemClickListener.onItemClick(view, position, items.get(position), TestBaseActivity.SAMPLE_BG[position], TestBaseActivity.SAMPLE_CIRCLE[position], true);
                            }



                           /* GradientDrawable draw = new GradientDrawable();
                            draw.setShape(GradientDrawable.RECTANGLE);
                            draw.setCornerRadius(5);
                            draw.setStroke(2,androidColors[position]);
                            draw.setColor(ctx.getResources().getColor(R.color.white));*/
                            //   ((OriginalViewHolder) holder).llmain.setBackground((ctx.getResources().getIntArray(R.array.sample_bg))[position]);

                         /*   GradientDrawable drawtext = new GradientDrawable();
                            drawtext.setShape(GradientDrawable.OVAL);
                            drawtext.setSize(60,60);
                            drawtext.setColor(androidColors[position]);*/

                            //((OriginalViewHolder) holder).optionIconTV.setBackgroundColor((ctx.getResources().getIntArray(R.array.sample_circle))[position]);


                        }

                    }
                }
            });

          /*
            // Start a drag whenever the handle view it touched
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

}