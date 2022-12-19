package com.utkarshnew.android.Utils;

import static com.utkarshnew.android.Utils.Helper.getHtmlUpdatedData;
import static com.utkarshnew.android.Utils.Helper.showWebData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.utkarshnew.android.courses.adapter.EMIListAdapter;
import com.utkarshnew.android.Model.Courses.EMIInfo;
import com.utkarshnew.android.R;

import java.util.List;

public class DialogUtils {

    public static void makeDialog(Context context, String titleTxt, String messageTxt,
                                  String submitTxt, String cancelTxt, boolean isCancelable,
                                  final onDialogUtilsOkClick okClick,
                                  final onDialogUtilsCancelClick cancelClick) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(isCancelable);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_alert_simple);
            TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
            TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

            if (TextUtils.isEmpty(titleTxt)) {
                titleDialog.setVisibility(View.GONE);
            } else {
                titleDialog.setVisibility(View.VISIBLE);
                titleDialog.setText(titleTxt);
            }
            msgDialog.setText(messageTxt);
            btn_cancel.setText(cancelTxt);
            btn_submit.setText(submitTxt);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (okClick != null)
                        okClick.onOKClick();
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (cancelClick != null)
                        cancelClick.onCancelClick();
                }
            });
            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }

    public static void makeCompleteCPDialog(Context context, String titleTxt, String messageTxt,
                                            String submitTxt, String cancelTxt, boolean isCancelable,
                                            final onDialogUtilsOkClick okClick) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(isCancelable);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_change_password_cmplt);
            TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
            TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
            btn_submit.setText(submitTxt);

            if (TextUtils.isEmpty(titleTxt)) {
                titleDialog.setVisibility(View.GONE);
            } else {
                titleDialog.setVisibility(View.VISIBLE);
            }

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (okClick != null)
                        okClick.onOKClick();
                }
            });

            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }

    public static void makeSingleButtonDialog(Activity context, String titleTxt, String messageTxt,
                                              String submitTxt, boolean isCancelable,
                                              final onDialogUtilsOkClick okClick) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(isCancelable);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_alert_single_button);
            TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
            //TextView msgDialog= (TextView) dialog.findViewById(R.id.msgDialog);
            WebView msgDialog = (WebView) dialog.findViewById(R.id.msgDialog);
            msgDialog.setBackgroundColor(context.getResources().getColor(R.color.dot_white));
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

            if (TextUtils.isEmpty(titleTxt)) {
                titleDialog.setVisibility(View.GONE);
            } else {
                titleDialog.setVisibility(View.VISIBLE);
                titleDialog.setText(titleTxt);
            }
            //msgDialog.setText(messageTxt);
            //msgDialog.loadData(TextUtils.isEmpty(messageTxt.trim())?"N/A":messageTxt.trim(), "text/html", "UTF-8");
            showWebData(context, getHtmlUpdatedData(messageTxt), msgDialog);
            //msgDialog.loadData(getHtmlUpdatedData(messageTxt), "text/html; charset=UTF-8", "base64");
            btn_submit.setText(submitTxt);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (okClick != null)
                        okClick.onOKClick();
                }
            });
            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }

    public static void makeEmiPaymentDialog(Context context, String title, String emptyMessage, final List<EMIInfo> list,
                                            final onDialogUtilsListItemClick itemClick, final onDialogUtilsCancelClick cancelClick) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setLayout(100, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.playlist_utils_emi_dialog);
            //dialog.findViewById(R.id.rl).setBackgroundResource(R.drawable.rectangle_top);

            ListView listView = (ListView) dialog.findViewById(R.id.list);
            CardView imageCloseIV = (CardView) dialog.findViewById(R.id.imageCloseIV);
            Button continueBtn = (Button) dialog.findViewById(R.id.continueBtn);
            continueBtn.setText("PAY NOW");
            TextView titleText = (TextView) dialog.findViewById(R.id.title);
            titleText.setText(title);
            if (list != null) {
                if (list.size() == 0) {
                    listView.setVisibility(View.GONE);
                } else {
                    EMIListAdapter adapter = new EMIListAdapter(context, list);
                    listView.setAdapter(adapter);
                    //listView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dialog_list_item, list));
                }
            } else {
                listView.setVisibility(View.GONE);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialog.dismiss();
                    if (itemClick != null)
                        itemClick.onListItemClick(position);
                }
            });

            imageCloseIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    /*if (cancelClick!=null)
                        cancelClick.onCancelClick();*/
                }
            });

            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (cancelClick != null)
                        cancelClick.onCancelClick();
                }
            });
            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }


    public static void makeEmiListDialog(Context context, String title, String emptyMessage, final List<EMIInfo> list,
                                         final onDialogUtilsListItemClick itemClick, final onDialogUtilsCancelClick cancelClick) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setLayout(100, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.playlist_utils_emi_dialog);
            //dialog.findViewById(R.id.rl).setBackgroundResource(R.drawable.rectangle_top);

            ListView listView = (ListView) dialog.findViewById(R.id.list);
            CardView imageCloseIV = (CardView) dialog.findViewById(R.id.imageCloseIV);
            Button continueBtn = (Button) dialog.findViewById(R.id.continueBtn);
            TextView titleText = (TextView) dialog.findViewById(R.id.title);
            TextView totalPriceTV = dialog.findViewById(R.id.totalPrice);
            titleText.setText(title);
            float totalPrice = 0;
            for (EMIInfo emiInfo : list) {
                totalPrice += Float.parseFloat(emiInfo.getEmiMrp());
            }
            totalPriceTV.setText(String.format("%s %s %s", "Total Price: ", context.getResources().getString(R.string.rs), totalPrice));
            if (list != null) {
                if (list.size() == 0) {
                    listView.setVisibility(View.GONE);
                } else {
                    EMIListAdapter adapter = new EMIListAdapter(context, list);
                    listView.setAdapter(adapter);
                    //listView.setAdapter(new ArrayAdapter<>(context, R.layout.simple_dialog_list_item, list));
                }
            } else {
                listView.setVisibility(View.GONE);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*dialog.dismiss();
                if (itemClick!=null)
                    itemClick.onListItemClick(position);*/
                }
            });

            imageCloseIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    /*if (cancelClick!=null)
                        cancelClick.onCancelClick();*/
                }
            });

            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (cancelClick != null)
                        cancelClick.onCancelClick();
                }
            });
            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }


    public interface onDialogUtilsOkClick {
        void onOKClick();
    }

    public interface onDialogUtilsOkTestFilterClick {
        void onOKTestFilterClick(boolean one, boolean two, boolean three);
    }

    public interface onDialogUtilsCancelClick {
        void onCancelClick();
    }

    public interface onDialogUtilsInputOkClick {
        void onInputOKClick(String input, String input1, String input2, String input3, String input4);

        void onInputOKClick(String input, String input1, String input2);

        void onInputOKClick(String trim, String trim1);

        void onInputOKClick(String trim);
    }

    public interface onDialogUtilsListItemClick {
        void onListItemClick(int position);
    }

    public interface onDialogUtilsSkipClick {
        void onSkipClick();
    }

    public interface onDialogUtilsButtonOneClick {
        void onButtonOneClick();
    }

    public interface onDialogUtilsButtonTwoClick {
        void onButtonTwoClick();
    }

    public interface onDialogUtilsButtonThreeClick {
        void onButtonThreeClick();
    }

    public interface onDialogUtilsRatingBarClick {
        void onRatingBarClick();
    }

    public interface onDialogUtilsExitClick {
        void onExitClick(boolean neverShowAgain);
    }

    public interface addViewCallback {
        void showAdd();
    }

    public interface addViewControl {
        void onAddShow(addViewCallback addViewCback);
    }

}