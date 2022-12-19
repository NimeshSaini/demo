package com.utkarshnew.android.testmodule.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.testmodule.model.Question;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.github.kexanie.library.MathView;

@SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
public class QuestionPagerAdaptor extends PagerAdapter implements View.OnClickListener {

    Context context;

    private int MAX_SIZE = 3;
    private ArrayList<SoftReference<View>> pageCache = new ArrayList<SoftReference<View>>(3);

    private List<Question> questionObjectArrayList;


    public QuestionPagerAdaptor(Context context, List<Question> questionObjectArrayList) {
        this.context = context;
        this.questionObjectArrayList = questionObjectArrayList;
    }

    @Override
    public int getCount() {
        return questionObjectArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        View view = fetchFromCache();
        if (view == null) {
            Log.d("prince", "" + position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.viewpager_items_qt7_new, container,
                    false);
        }
        multipleChoice_7_View(view, container, position);

        return view;

    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }

    private View fetchFromCache() {
        for (int n = (pageCache.size() - 1); n >= 0; n--) {
            SoftReference<View> reference = pageCache.remove(n);
            View view = reference.get();
            if (view != null) {
                return view;
            }
        }
        return null;
    }


    private View multipleChoice_7_View(View itemView, ViewGroup container, int position) {


        String showMarksstr = null;
        String template = null;
        String isOptionShuffle = null;


        TextView txt_qno, right_marks, wrong_marks;
        ClickableWebView essaywebview, questionwebview;

        MathView option1_webview, option2_webview, option3_webview, option4_webview, option5_webview, option6_webview, option7_webview, option8_webview;
        RelativeLayout ll_option1, ll_option2, ll_option3, ll_option4, ll_option5, ll_option6, ll_option7, ll_option8;
        View view_divider1, view_divider2, view_divider3, view_divider4, view_divider5, view_divider6, view_divider7, view_divider8;
/*

        txt_qno = itemView.findViewById(R.id.txt_qno);
        int int_questno = position + 1;
        String qnnostr = "Question " + int_questno + " out of " + questionObjectArrayList.size();
        txt_qno.setText(qnnostr);
*/

    /*    right_marks = itemView.findViewById(R.id.right_marks);
        right_marks.setText("max "+questionObjectArrayList.get(position).getId());

        wrong_marks = itemView.findViewById(R.id.wrong_marks);
        wrong_marks.setText("min "+questionObjectArrayList.get(position).getConfigId());
*/


        essaywebview = itemView.findViewById(R.id.essaywebview);
        essaywebview.getSettings().setJavaScriptEnabled(true);


        questionwebview = itemView.findViewById(R.id.questionwebview);
        questionwebview.getSettings().setJavaScriptEnabled(true);


        ll_option1 = itemView.findViewById(R.id.ll_option1);
        ll_option1.setVisibility(View.GONE);
        ll_option1.setOnClickListener(this);

        ll_option2 = itemView.findViewById(R.id.ll_option2);
        ll_option2.setVisibility(View.GONE);
        ll_option2.setOnClickListener(this);

        ll_option3 = itemView.findViewById(R.id.ll_option3);
        ll_option3.setVisibility(View.GONE);
        ll_option3.setOnClickListener(this);

        ll_option4 = itemView.findViewById(R.id.ll_option4);
        ll_option4.setVisibility(View.GONE);
        ll_option4.setOnClickListener(this);

        ll_option5 = itemView.findViewById(R.id.ll_option5);
        ll_option5.setVisibility(View.GONE);
        ll_option5.setOnClickListener(this);

        ll_option6 = itemView.findViewById(R.id.ll_option6);
        ll_option6.setVisibility(View.GONE);
        ll_option6.setOnClickListener(this);

        ll_option7 = itemView.findViewById(R.id.ll_option7);
        ll_option7.setVisibility(View.GONE);
        ll_option7.setOnClickListener(this);

        ll_option8 = itemView.findViewById(R.id.ll_option8);
        ll_option8.setVisibility(View.GONE);
        ll_option8.setOnClickListener(this);

        view_divider1 = itemView.findViewById(R.id.view_divider1);
        view_divider1.setVisibility(View.GONE);

        view_divider2 = itemView.findViewById(R.id.view_divider2);
        view_divider2.setVisibility(View.GONE);

        view_divider3 = itemView.findViewById(R.id.view_divider3);
        view_divider3.setVisibility(View.GONE);

        view_divider4 = itemView.findViewById(R.id.view_divider4);
        view_divider4.setVisibility(View.GONE);

        view_divider5 = itemView.findViewById(R.id.view_divider5);
        view_divider5.setVisibility(View.GONE);

        view_divider6 = itemView.findViewById(R.id.view_divider6);
        view_divider6.setVisibility(View.GONE);

        view_divider7 = itemView.findViewById(R.id.view_divider7);
        view_divider7.setVisibility(View.GONE);

        view_divider8 = itemView.findViewById(R.id.view_divider8);
        view_divider8.setVisibility(View.GONE);

        option1_webview = itemView.findViewById(R.id.option1_webview);
        option1_webview.getSettings().setJavaScriptEnabled(true);
        option1_webview.addJavascriptInterface(new WebAppInterface(context, option1_webview, ll_option1), "Android");

        option2_webview = itemView.findViewById(R.id.option2_webview);
        option2_webview.getSettings().setJavaScriptEnabled(true);
        option2_webview.addJavascriptInterface(new WebAppInterface(context, option2_webview, ll_option2), "Android");

        option3_webview = itemView.findViewById(R.id.option3_webview);
        option3_webview.getSettings().setJavaScriptEnabled(true);
        option3_webview.addJavascriptInterface(new WebAppInterface(context, option3_webview, ll_option3), "Android");

        option4_webview = itemView.findViewById(R.id.option4_webview);
        option4_webview.getSettings().setJavaScriptEnabled(true);
        option4_webview.addJavascriptInterface(new WebAppInterface(context, option4_webview, ll_option4), "Android");

        option5_webview = itemView.findViewById(R.id.option5_webview);
        option5_webview.getSettings().setJavaScriptEnabled(true);
        option5_webview.addJavascriptInterface(new WebAppInterface(context, option5_webview, ll_option5), "Android");

        option6_webview = itemView.findViewById(R.id.option6_webview);
        option6_webview.getSettings().setJavaScriptEnabled(true);
        option6_webview.addJavascriptInterface(new WebAppInterface(context, option6_webview, ll_option6), "Android");

        option7_webview = itemView.findViewById(R.id.option7_webview);
        option7_webview.getSettings().setJavaScriptEnabled(true);
        option7_webview.addJavascriptInterface(new WebAppInterface(context, option7_webview, ll_option7), "Android");

        option8_webview = itemView.findViewById(R.id.option8_webview);
        option8_webview.getSettings().setJavaScriptEnabled(true);
        option8_webview.addJavascriptInterface(new WebAppInterface(context, option8_webview, ll_option8), "Android");

        essaywebview.setVisibility(View.GONE);

        String qhtml = questionObjectArrayList.get(position).getQuestion();
        // Helper.load(questionwebview, qhtml);
        setHtmlinWebView(questionwebview, qhtml);


        ArrayList<OptionObject> optionsArray = new ArrayList<>();

        if (!questionObjectArrayList.get(position).getOption1().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption1());
            optionObject.setOptionAlphabet("a");

            optionsArray.add(optionObject);
        }
        if (!questionObjectArrayList.get(position).getOption2().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption2());
            optionObject.setOptionAlphabet("b");

            optionsArray.add(optionObject);
        }
        if (!questionObjectArrayList.get(position).getOption3().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption3());
            optionObject.setOptionAlphabet("c");

            optionsArray.add(optionObject);
        }
        if (!questionObjectArrayList.get(position).getOption4().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption4());
            optionObject.setOptionAlphabet("d");

            optionsArray.add(optionObject);
        }
        if (questionObjectArrayList.get(position).getOption5() != null && !questionObjectArrayList.get(position).getOption5().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption5());
            optionObject.setOptionAlphabet("e");

            optionsArray.add(optionObject);
        }
        if (questionObjectArrayList.get(position).getOption6() != null && !questionObjectArrayList.get(position).getOption6().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption6());
            optionObject.setOptionAlphabet("f");

            optionsArray.add(optionObject);
        }
        if (questionObjectArrayList.get(position).getOption7() != null && !questionObjectArrayList.get(position).getOption7().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption7());
            optionObject.setOptionAlphabet("g");

            optionsArray.add(optionObject);
        }
        if (questionObjectArrayList.get(position).getOption8() != null && !questionObjectArrayList.get(position).getOption8().isEmpty()) {
            OptionObject optionObject = new OptionObject();
            optionObject.setOptionText(questionObjectArrayList.get(position).getOption8());
            optionObject.setOptionAlphabet("h");

            optionsArray.add(optionObject);
        }

        if (isOptionShuffle != null) {
            if (isOptionShuffle.equalsIgnoreCase("1")) {
                // Option Shuffling enabled
                long seed = System.nanoTime();
                Collections.shuffle(optionsArray, new Random(seed));
            }
        }


        if (!questionObjectArrayList.get(position).getOption1().isEmpty()) {
            String o1html = optionsArray.get(0).getOptionText();
            // Helper.load(option1_webview, o1html);
            setHtmlinWebView(option1_webview, o1html);


            int val = gettagnumberfromalphabet(optionsArray.get(0).getOptionAlphabet());
            ll_option1.setTag(position + 1 + "9108" + "" + val);
            ll_option1.setVisibility(View.VISIBLE);
            view_divider1.setVisibility(View.VISIBLE);
        }

        if (!questionObjectArrayList.get(position).getOption2().isEmpty()) {

            String o1html = optionsArray.get(1).getOptionText();
            // Helper.load(option2_webview, o1html);

            setHtmlinWebView(option2_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(1).getOptionAlphabet());
            ll_option2.setTag(position + 1 + "9108" + "" + val);
            ll_option2.setVisibility(View.VISIBLE);
            view_divider2.setVisibility(View.VISIBLE);
        }

        if (!questionObjectArrayList.get(position).getOption3().isEmpty()) {

            String o1html = optionsArray.get(2).getOptionText();
            //Helper.load(option3_webview, o1html);
            setHtmlinWebView(option3_webview, o1html);


            int val = gettagnumberfromalphabet(optionsArray.get(2).getOptionAlphabet());
            ll_option3.setTag(position + 1 + "9108" + "" + val);
            ll_option3.setVisibility(View.VISIBLE);
            view_divider3.setVisibility(View.VISIBLE);
        }

        if (!questionObjectArrayList.get(position).getOption4().isEmpty()) {

            String o1html = optionsArray.get(3).getOptionText();
            // Helper.load(option4_webview, o1html);
            setHtmlinWebView(option4_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(3).getOptionAlphabet());
            ll_option4.setTag(position + 1 + "9108" + "" + val);
            ll_option4.setVisibility(View.VISIBLE);
            view_divider4.setVisibility(View.VISIBLE);
        }

        if (questionObjectArrayList.get(position).getOption5() != null && !questionObjectArrayList.get(position).getOption5().isEmpty()) {

            String o1html = optionsArray.get(4).getOptionText();
            Helper.load(option5_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(4).getOptionAlphabet());
            ll_option5.setTag(position + 1 + "9108" + "" + val);
            ll_option5.setVisibility(View.VISIBLE);
            view_divider5.setVisibility(View.VISIBLE);
        }

        if (questionObjectArrayList.get(position).getOption6() != null && !questionObjectArrayList.get(position).getOption6().isEmpty()) {

            String o1html = optionsArray.get(5).getOptionText();
            Helper.load(option6_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(5).getOptionAlphabet());
            ll_option6.setTag(position + 1 + "9108" + "" + val);
            ll_option6.setVisibility(View.VISIBLE);
            view_divider6.setVisibility(View.VISIBLE);
        }

        if (questionObjectArrayList.get(position).getOption7() != null && !questionObjectArrayList.get(position).getOption7().isEmpty()) {

            String o1html = optionsArray.get(6).getOptionText();
            Helper.load(option7_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(6).getOptionAlphabet());
            ll_option7.setTag(position + 1 + "9108" + "" + val);
            ll_option7.setVisibility(View.VISIBLE);
            view_divider7.setVisibility(View.VISIBLE);
        }

        if (questionObjectArrayList.get(position).getOption8() != null && !questionObjectArrayList.get(position).getOption8().isEmpty()) {

            String o1html = optionsArray.get(7).getOptionText();
            Helper.load(option8_webview, o1html);

            int val = gettagnumberfromalphabet(optionsArray.get(7).getOptionAlphabet());
            ll_option8.setTag(position + 1 + "9108" + "" + val);
            ll_option8.setVisibility(View.VISIBLE);
            view_divider8.setVisibility(View.VISIBLE);
        }


        container.addView(itemView);

        return itemView;
    }

    private int gettagnumberfromalphabet(String str) {
        int val = 0;

        if (str.trim().equalsIgnoreCase("a")) {
            val = 1;
        } else if (str.trim().equalsIgnoreCase("b")) {
            val = 2;
        } else if (str.trim().equalsIgnoreCase("c")) {
            val = 3;
        } else if (str.trim().equalsIgnoreCase("d")) {
            val = 4;
        } else if (str.trim().equalsIgnoreCase("e")) {
            val = 5;
        } else if (str.trim().equalsIgnoreCase("f")) {
            val = 6;
        } else if (str.trim().equalsIgnoreCase("g")) {
            val = 7;
        } else if (str.trim().equalsIgnoreCase("h")) {
            val = 8;
        }

        return val;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ScrollView) object);
        addToCache((View) object);

    }

    private void addToCache(View view) {
        if (pageCache.size() < MAX_SIZE) {
            pageCache.add(new SoftReference<View>(view));
        } else {
            for (int n = (pageCache.size() - 1); n >= 0; n--) {
                SoftReference<View> cachedView = pageCache.get(n);
                if (cachedView.get() == null) {
                    pageCache.set(n, new SoftReference<View>(view));
                    return;
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

        String clickedstr = v.getTag().toString().trim();
        String[] arr = clickedstr.split("9108");
        Question ques = questionObjectArrayList.get(Integer.parseInt(arr[0]) - 1);

        if (ques.getQuestionType().equalsIgnoreCase("SC") || ques.getQuestionType().equalsIgnoreCase("MC")) {
            multipleChoice_7_clicked(v);
        }


    }

    public void multipleChoice_7_clicked(View v) {
        String clickedstr = v.getTag().toString().trim();
        String[] arr = clickedstr.split("9108");
        Question ques = questionObjectArrayList.get(Integer.parseInt(arr[0]) - 1);

        String selectedOptionAlphabet = "";

        for (int j = 1; j <= 8; j++) {
            String tagstr = arr[0] + "9108" + j;
            if (tagstr.equalsIgnoreCase(clickedstr)) {
//                Question questionObject = mydb.getSingleTestData(ques.getID_PRIMARY());
//                String str = questionObject.getSTUDENT_ANSWER_RESPONSE().trim();
                String str = "";
                if (arr[1].equalsIgnoreCase("1")) {
                    selectedOptionAlphabet = "a";
                } else if (arr[1].equalsIgnoreCase("2")) {
                    selectedOptionAlphabet = "b";
                } else if (arr[1].equalsIgnoreCase("3")) {
                    selectedOptionAlphabet = "c";
                } else if (arr[1].equalsIgnoreCase("4")) {
                    selectedOptionAlphabet = "d";
                } else if (arr[1].equalsIgnoreCase("5")) {
                    selectedOptionAlphabet = "e";
                } else if (arr[1].equalsIgnoreCase("6")) {
                    selectedOptionAlphabet = "f";
                } else if (arr[1].equalsIgnoreCase("7")) {
                    selectedOptionAlphabet = "g";
                } else if (arr[1].equalsIgnoreCase("8")) {
                    selectedOptionAlphabet = "h";
                }

                RelativeLayout optview = v.findViewWithTag(clickedstr);
                TextView textView_option = (TextView) optview.getChildAt(0);

                if (selectedOptionAlphabet.equalsIgnoreCase(str)) {
                    final int sdk = Build.VERSION.SDK_INT;

                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "1")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "2")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "3")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "4")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "5")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "6")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        }
                    } else {
                        if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "1")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "2")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "3")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "4")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "5")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "6")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        }
                    }

                    selectedOptionAlphabet = "";
                } else {
                    final int sdk = Build.VERSION.SDK_INT;
                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_select_plan));
                    } else {
                        textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_select_plan));
                    }
                }

                // mydb.updateTestData(ques.getID_PRIMARY(), selectedOptionAlphabet);
            } else {

                LinearLayout qttype_7_opitions_ll = (LinearLayout) v.getParent();
                RelativeLayout optview = qttype_7_opitions_ll.findViewWithTag(tagstr);

                if (optview != null) {
                    TextView textView_option = (TextView) optview.getChildAt(0);
                    final int sdk = Build.VERSION.SDK_INT;
                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "1")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "2")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "3")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "4")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "5")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "6")) {
                            textView_option.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        }
                    } else {
                        if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "1")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "2")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "3")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "4")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "5")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        } else if (tagstr.equalsIgnoreCase(arr[0] + "9108" + "6")) {
                            textView_option.setBackground(context.getResources().getDrawable(R.drawable.radio_deselct_plan));
                        }
                    }
                }
            }
        }
    }


    public class WebAppInterface {
        Context mContext;
        WebView web;
        RelativeLayout vgg;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c, WebView wv, RelativeLayout vg) {
            mContext = c;
            web = wv;
            vgg = vg;
        }

        @JavascriptInterface   // must be added for API 17 or higher
        public void showToast(String toast) {
            ((AppCompatActivity) context).runOnUiThread(() -> {
                // Stuff that updates the UI
                vgg.performClick();
            });
        }
    }

    private void setHtmlinWebView(WebView webView, String html) {
        try {
            String str = "<div onClick=\"showAndroidToast('Hello Android!')\" >" + html + "</div>\n" +
                    "\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    function showAndroidToast(toast) {\n" +
                    "        Android.showToast(toast);\n" +
                    "    }\n" +
                    "</script>";

            webView.loadDataWithBaseURL("",
                    "<html>" +
                            "<body>" +
                            str +
                            "</body>" +
                            "</html>", "text/html; charset=UTF-8", null, "");

            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

            webView.setOnLongClickListener(v -> {
                // For final release of your app, comment the toast notification
                return true;
            });

            webView.setLongClickable(false);
            webView.setHapticFeedbackEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}