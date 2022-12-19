package com.utkarshnew.android.courses.Fragment;

import static com.utkarshnew.android.Utils.Helper.buyNowCourses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.adapter.AllCoursesAdapater;
import com.utkarshnew.android.courses.adapter.CommonListAdapter;
import com.utkarshnew.android.courses.adapter.IBTPracticeViewAllAdapter;
import com.utkarshnew.android.courses.Interfaces.OnMyCartItemListener;
import com.utkarshnew.android.courses.modal.CartItems;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.Basic;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.Model.Courses.CourseCategory;
import com.utkarshnew.android.Model.Courses.CoursesData;
import com.utkarshnew.android.Model.Courses.FAQ;
import com.utkarshnew.android.Model.Courses.SinglestudyModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.Model.PoJoModel.CatDataOnePOJO;
import com.utkarshnew.android.Model.PoJoModel.SingleStudyPOJO;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CommonFragForList extends MainFragment implements OnMyCartItemListener {
    private static final String TAG = "CommonFragForList";
    public String frag_type = "", errorMessage = "", apiType;
    public String emiCourseId;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    public int previousTotalItemCount;
    public boolean isSearching = false;
    public String last_course_id, last_test_id, last_category_id;
    LinearLayoutManager linearLayoutManager;
    Activity activity;
    CourseCategory courseCategory;
    ArrayList<Course> courseArrayList;
    ArrayList<Courselist> courseArrayLists;
    ArrayList<Course> searchArrayList;
    ArrayList<CoursesData> coursesDataArrayList;
    ArrayList<FAQ> faqArrayList;
    ArrayList<ResultTestSeries> resultTestSeriesArrayList;
    CommonListAdapter commonListAdapter;
    AllCoursesAdapater allCoursesAdapater;
    Course course;
    int isalreadyconnected = 0;
    IBTPracticeViewAllAdapter adapter;
    private RecyclerView commonListRV;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private TextView errorTV;
    private String searchContent;
    private String courseIds = "";

    TextView price, mrpCutTV;
    RelativeLayout buttonLow;
    LinearLayout priceLL;
    TextView buyNowBtn;
    ArrayList<SinglestudyModel> singlestudyModels = new ArrayList<>();

    public CommonFragForList() {
        // Required empty public constructor
    }

    public static CommonFragForList newInstance(String frag_type, String courseId) {
        CommonFragForList commonFragForList = new CommonFragForList();
        Bundle args = new Bundle();
        args.putSerializable(Const.COURSE_ID, courseId);
        args.putString(Const.FRAG_TYPE, frag_type);
        commonFragForList.setArguments(args);
        return commonFragForList;
    }

    public static CommonFragForList newInstance(String frag_type, CourseCategory courseCategory) {
        CommonFragForList commonFragForList = new CommonFragForList();
        Bundle args = new Bundle();
        args.putSerializable(Const.CATEGORY, courseCategory);
        args.putString(Const.FRAG_TYPE, frag_type);
        commonFragForList.setArguments(args);
        return commonFragForList;
    }

    public static CommonFragForList newInstance(String frag_type, CourseCategory courseCategory, String searchKeyword) {
        CommonFragForList commonFragForList = new CommonFragForList();
        Bundle args = new Bundle();
        args.putSerializable(Const.CATEGORY, courseCategory);
        args.putString(Const.FRAG_TYPE, frag_type);
        args.putString(Const.SEARCH_CONTENT, searchKeyword);
        commonFragForList.setArguments(args);
        return commonFragForList;
    }

    // this for FAQ
    public static CommonFragForList newInstance(String frag_type, Course course) {
        CommonFragForList commonFragForList = new CommonFragForList();
        Bundle args = new Bundle();
        args.putSerializable(Const.COURSES, course);
        args.putString(Const.FRAG_TYPE, frag_type);
        commonFragForList.setArguments(args);
        return commonFragForList;
    }

    public static CommonFragForList newInstance(String frag_type) {
        CommonFragForList commonFragForList = new CommonFragForList();
        Bundle args = new Bundle();
        args.putString(Const.FRAG_TYPE, frag_type);
        commonFragForList.setArguments(args);
        return commonFragForList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faqArrayList = new ArrayList<>();
        courseArrayList = new ArrayList<>();
        courseArrayLists = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        coursesDataArrayList = new ArrayList<>();
        resultTestSeriesArrayList = new ArrayList<>();
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            emiCourseId = getArguments().getString(Const.COURSE_ID);
            courseCategory = (CourseCategory) getArguments().getSerializable(Const.CATEGORY);
            course = (Course) getArguments().getSerializable(Const.COURSES);
            if (getArguments().containsKey(Const.SEARCH_CONTENT))
                searchContent = getArguments().getString(Const.SEARCH_CONTENT);
        }

        activity = getActivity();
        /*if (frag_type.equals(Const.MYCART)) {
            ArrayList<CartItems> cartItemsList = Helper.getMyCourseCart(activity);
            List<String> myCourseIds = new ArrayList<>();
            if (cartItemsList.size()>=1) {
                for (CartItems cartItems : cartItemsList) {
                    myCourseIds.add(cartItems.getCourse_id());
                }
                courseIds = TextUtils.join(",", myCourseIds);
                //Toast.makeText(activity, ""+courseIds, Toast.LENGTH_SHORT).show();
            }else {
                courseIds = "";
            }
        }*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        commonListRV.setLayoutManager(linearLayoutManager);

        // to Make API request on the basis of frag_type
        if (!frag_type.equals(Const.MYCART) || !frag_type.equals(Const.MYCART)) {
            getDatas(true);
        }

        commonListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (totalItemCount >= 10) { // if the list is more than 10 then only pagination will work
                    if (loading) {
                        if (totalItemCount > previousTotalItemCount) {
                            loading = false;
                            previousTotalItemCount = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {

                        int i = 0;
                        while (i < totalItemCount) {
                            if (isSearching)
                                last_course_id = searchArrayList.get(totalItemCount - 1 - i).getId();
                            i = totalItemCount;
                        }
                        if (isalreadyconnected == 0 && isSearching) {
                            getDatas(false);
                            isalreadyconnected = 1;
                        }
                        loading = true;
                    }
                }
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check Guest User
                if (SharedPreference.getInstance().getLoggedInUser().getId().equalsIgnoreCase("0")) {
                    Toast.makeText(activity, getResources().getString(R.string.guest_login_toast), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent courseInvoice = new Intent(activity, CourseActivity.class); // FRAG_TYPE, Const.COURSE_INVOICE CourseInvoice
                courseInvoice.putExtra(Const.FRAG_TYPE, Const.COURSE_INVOICE);
                courseInvoice.putExtra(Const.EMI_TYPE, Const.EMI_FULL);
                courseInvoice.putExtra(Const.SINGLE_STUDY, singlestudyModels);
                startActivity(courseInvoice);
            }
        });
    }

    public void initButton(ArrayList<Courselist> course) {
        if (course.size() > 0) {
            setBasicData(course);
            if (frag_type.equals(Const.MYCART)) {
                buttonLow.setVisibility(View.VISIBLE);
            } else {
                buttonLow.setVisibility(View.GONE);
            }
            float totalPrice = 0;
            for (Courselist courselist : course) {
                totalPrice += Float.parseFloat(courselist.getCourseSp());
            }
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) priceLL.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            priceLL.setLayoutParams(params);

            buyNowBtn.setText(getString(R.string.checkout_now));
            mrpCutTV.setVisibility(View.GONE);
            price.setText("Total Amount " + activity.getResources().getString(R.string.rs) + "" + totalPrice + "/-");
        } else {
            buttonLow.setVisibility(View.GONE);
        }
    }

    public void setBasicData(ArrayList<Courselist> course) {
        singlestudyModels = new ArrayList<>();
        for (Courselist courselist : course) {
            Basic basic = new Basic(courselist.getCover_image(),
                    courselist.getId(),
                    courselist.getTitle(),
                    courselist.getCourseSp(),
                    courselist.getDescHeaderImage(),
                    courselist.getCourseAttribute(),
                    courselist.getValidity(),
                    courselist.getPayment_type(),
                    courselist.getColorCode(),
                    "",
                    courselist.getMrp(),
                    courselist.getCourseAttribute(),
                    "",
                    "",
                    courselist.getIs_postal_available(),
                    courselist.getHolderType(),
                    "");
            SinglestudyModel singleStudy = new SinglestudyModel(basic);
            singlestudyModels.add(singleStudy);
        }
    }

    public void getDatas(boolean show) {
        if (isSearching) {
            NetworkAPICall(API.API_SEARCH_COURSE, "", show, false, false);
        } else if (frag_type.equals(Const.ALLCOURSES)) {
            NetworkAPICall(API.API_GET_LANDING_PAGE_DATA, "", show, false, false);
        } else if (frag_type.equals(Const.SEEALL_COURSE)) {
            NetworkAPICall(API.API_GET_ALL_CATEGORY_DATA, "", show, false, false);
        } else if (frag_type.equals(Const.MYCOURSES)) {
            NetworkAPICall(API.API_GET_MY_COURSE_DATA, "", show, false, false);
        } else if (frag_type.equals(Const.FAQ)) {
            NetworkAPICall(API.API_GET_FAQ_DATA, "", show, false, false);
        } else if (frag_type.equals(Const.LEADERBOARD)) {
            NetworkAPICall(API.API_GET_USER_GIVEN_TESTSERIES, "", show, false, false);
        } else if (frag_type.equals(Const.PRACTICE)) {
            NetworkAPICall(API.API_SEARCH_COURSE, "", show, false, false);
        } else if (frag_type.equals(Const.SEARCH_COURSE)) {
            NetworkAPICall(API.API_SEARCH_COURSE_EXAM, "", show, false, false);
        } else if (frag_type.equals(Const.MYCART)) {
            NetworkAPICall(API.API_CART_COURSE_EXAM, "", show, false, false);
        } else if (frag_type.equals(Const.MYEMICOURSES)) {
            NetworkAPICall(API.API_GET_BASIC_COURSE, "", show, false, false);
        }
    }

    private void initViews(View view) {
        commonListRV = (RecyclerView) view.findViewById(R.id.courseListRV);
        errorTV = (TextView) view.findViewById(R.id.errorTV);

        mrpCutTV = view.findViewById(R.id.mrpCutTV);
        price = view.findViewById(R.id.priceTV);
        buyNowBtn = view.findViewById(R.id.buyNowBtn);
        buttonLow = view.findViewById(R.id.buttonLow);
        priceLL = view.findViewById(R.id.priceLL);
    }

    @Override
    public void onResume() {
        activity = getActivity();
        if (frag_type.equals(Const.MYCART)) {
            ArrayList<CartItems> cartItemsList = Helper.getMyCourseCart(activity);
            List<String> myCourseIds = new ArrayList<>();
            if (cartItemsList.size() >= 1) {
                for (CartItems cartItems : cartItemsList) {
                    myCourseIds.add(cartItems.getCourse_id());
                }
                courseIds = TextUtils.join(",", myCourseIds);
                //Toast.makeText(activity, ""+courseIds, Toast.LENGTH_SHORT).show();
            } else {
                courseIds = "";
            }
            getDatas(true);

            if (SharedPreference.getInstance().getBoolean(Const.IS_PAYMENT_DONE)) {
                buttonLow.setVisibility(View.GONE);
                SharedPreference.getInstance().putBoolean(Const.SINGLE_STUDY, true);
                //SharedPreference.getInstance().putBoolean(Const.IS_PAYMENT_DONE, false);
            }
        } else {
            buttonLow.setVisibility(View.GONE);
        }
        super.onResume();
    }

    /*@Override
    public Call<String> getAPIB(String apitype, APIInterface service) {
        return null;
    }*/

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_LANDING_PAGE_DATA:
                return service.getLandingPageData(/*SharedPreference.getInstance().getLoggedInUser().getId()*/);

            case API.API_GET_ALL_CATEGORY_DATA:
                CatDataOnePOJO doseMenuPOJO = new CatDataOnePOJO(courseCategory.getId());
                String doseStr = new Gson().toJson(doseMenuPOJO);
                String doseStrScr = AES.encrypt(doseStr);

                //return service.getAllCategoryData(/*SharedPreference.getInstance().getLoggedInUser().getId(),*/courseCategory.getId());
                return service.getAllCategoryData(doseStrScr);

            case API.API_SEARCH_COURSE:
                return service.searchCourse(/*SharedPreference.getInstance().getLoggedInUser().getId(),*/
                        searchContent,
                        last_course_id);

            case API.API_SEARCH_COURSE_EXAM:
                return service.searchCourseExam(SharedPreference.getInstance().getLoggedInUser().getId(),
                        searchContent,
                        last_course_id);

            case API.API_CART_COURSE_EXAM:
                return service.cartCourseExam(courseIds);

            case API.API_GET_FAQ_DATA:
                return service.getFaqData(SharedPreference.getInstance().getLoggedInUser().getId(),
                        course.getId());

            case API.API_GET_MY_COURSE_DATA:
                return service.getMyCourseData(/*SharedPreference.getInstance().getLoggedInUser().getId()*/);

            case API.API_GET_BASIC_COURSE:
                SingleStudyPOJO doseMenuPOJO1 = new SingleStudyPOJO(emiCourseId, "");
                String doseStr1 = new Gson().toJson(doseMenuPOJO1);
                String doseStrScr1 = AES.encrypt(doseStr1);
                return service.getBasicCourse(doseStrScr1);

            case API.API_GET_USER_GIVEN_TESTSERIES:
                return service.getUserGivenTestSeries(SharedPreference.getInstance().getLoggedInUser().getId());
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        JSONArray dataArray;
        switch (apitype) {
            case API.API_GET_LANDING_PAGE_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    dataArray = jsonobject.getJSONArray(Const.DATA);
                    coursesDataArrayList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        CoursesData coursesData = gson.fromJson(dataArray.get(i).toString(), CoursesData.class);
                        coursesDataArrayList.add(coursesData);
                    }
                } else {
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitLandingPageAdapter();
                break;
            case API.API_GET_FAQ_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    dataArray = jsonobject.getJSONArray(Const.DATA);
                    faqArrayList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        FAQ faq = gson.fromJson(dataArray.get(i).toString(), FAQ.class);
                        faqArrayList.add(faq);
                    }
                } else {
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitFaqAdapter();
                break;

            case API.API_GET_ALL_CATEGORY_DATA:
                isalreadyconnected = 0;
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonobject.getJSONObject(Const.DATA);
                    dataArray = data.getJSONArray(Const.COURSE_LIST);
                    courseArrayList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        Course coursesData = gson.fromJson(dataArray.get(i).toString(), Course.class);
                        courseArrayList.add(coursesData);
                    }
                } else {
                    errorMessage = jsonobject.optString(Const.MESSAGE);
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitMyCourseAdapter();
                break;

            case API.API_SEARCH_COURSE:
                isalreadyconnected = 0;
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONArray data = jsonobject.getJSONArray(Const.DATA);
                    if (TextUtils.isEmpty(last_course_id)) {
                        searchArrayList = new ArrayList<>();
                    }
                    for (int i = 0; i < data.length(); i++) {
                        Course coursesData = gson.fromJson(data.get(i).toString(), Course.class);
                        searchArrayList.add(coursesData);
                    }
                } else {
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitSearchCourseAdapter();
                break;

            case API.API_SEARCH_COURSE_EXAM:
                isalreadyconnected = 0;
                JSONArray dataSearch = jsonobject.optJSONArray(Const.DATA);
                if (dataSearch != null && dataSearch.length() > 0) {
                    if (TextUtils.isEmpty(last_course_id)) {
                        courseArrayLists = new ArrayList<>();
                    }
                    for (int i = 0; i < dataSearch.length(); i++) {
                        Courselist coursesData = new Gson().fromJson(dataSearch.get(i).toString(), Courselist.class);
                        courseArrayLists.add(coursesData);
                    }
                    last_course_id = courseArrayLists.get(courseArrayLists.size() - 1).getId();

                } else {
                    errorMessage = jsonobject.optString(Const.MESSAGE);
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitMyCourseAdapter();
                break;

            case API.API_CART_COURSE_EXAM:
                isalreadyconnected = 0;
                JSONArray dataCart = jsonobject.optJSONArray(Const.DATA);
                if (dataCart != null && dataCart.length() > 0) {
                    courseArrayLists = new ArrayList<>();
                    for (int i = 0; i < dataCart.length(); i++) {
                        Courselist coursesData = new Gson().fromJson(dataCart.get(i).toString(), Courselist.class);
                        courseArrayLists.add(coursesData);
                    }

                } else {
                    courseArrayLists = new ArrayList<>();
                    errorMessage = jsonobject.optString(Const.MESSAGE);
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    //RetrofitResponse.GetApiData(activity,jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE));
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitMyCourseAdapter();
                break;

            case API.API_GET_MY_COURSE_DATA:
                isalreadyconnected = 0;
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonobject.getJSONObject(Const.DATA);
                    dataArray = data.getJSONArray(Const.COURSE_LIST);
                    courseArrayLists = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        Courselist coursesData = gson.fromJson(dataArray.get(i).toString(), Courselist.class);
                        courseArrayLists.add(coursesData);
                    }
                } else {
                    errorMessage = jsonobject.optString(Const.MESSAGE);
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    //RetrofitResponse.GetApiData(activity,jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE));
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitMyCourseAdapter();
                break;

            case API.API_GET_BASIC_COURSE:
                isalreadyconnected = 0;
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    SinglestudyModel singleStudy = gson.fromJson(jsonobject.optString(Const.DATA), SinglestudyModel.class);
                    buyNowCourses(activity, singleStudy);
                    activity.finish();
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;

            case API.API_GET_USER_GIVEN_TESTSERIES:
                isalreadyconnected = 0;
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    dataArray = jsonobject.getJSONArray(Const.DATA);
//                    if (TextUtils.isEmpty(last_test_id))
                    resultTestSeriesArrayList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        ResultTestSeries resultTestSeries = gson.fromJson(dataObj.toString(), ResultTestSeries.class);
                        resultTestSeriesArrayList.add(resultTestSeries);
                    }

                } else {
                    //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                if (jsonobject.optString(Const.AUTH_CODE) != null) {
                    if (jsonobject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                        return;
                    }
                }
                InitMyTestSeriesAdapter();
                break;
        }
    }

    private void InitFaqAdapter() {
        if (faqArrayList.size() > 0) {
            commonListAdapter = new CommonListAdapter(activity, frag_type, faqArrayList);
            commonListRV.setAdapter(commonListAdapter);
            errorTV.setVisibility(View.GONE);
            commonListRV.setVisibility(View.VISIBLE);
        } else {
            errorTV.setText(errorMessage);
            errorTV.setVisibility(View.VISIBLE);
            commonListRV.setVisibility(View.GONE);
        }
    }

    private void InitLandingPageAdapter() {
        if (coursesDataArrayList.size() > 0) {
            allCoursesAdapater = new AllCoursesAdapater(activity, coursesDataArrayList, CommonFragForList.this);
            commonListRV.setAdapter(allCoursesAdapater);
            errorTV.setVisibility(View.GONE);
            commonListRV.setVisibility(View.VISIBLE);
        } else {
            errorTV.setText(errorMessage);
            errorTV.setVisibility(View.VISIBLE);
            commonListRV.setVisibility(View.GONE);
        }
    }

    private void InitMyTestSeriesAdapter() {
        if (TextUtils.isEmpty(last_test_id)) {
            if (resultTestSeriesArrayList.size() > 0) {
                commonListAdapter = new CommonListAdapter(activity, resultTestSeriesArrayList);
                commonListRV.setAdapter(commonListAdapter);
                errorTV.setVisibility(View.GONE);
                commonListRV.setVisibility(View.VISIBLE);
            } else {
                errorTV.setText(errorMessage);
                errorTV.setVisibility(View.VISIBLE);
                commonListRV.setVisibility(View.GONE);
            }
        } else {
            commonListAdapter.notifyDataSetChanged();
        }
    }

    private void InitMyCourseAdapter() {
        if (courseArrayLists.size() > 0) {
            adapter = new IBTPracticeViewAllAdapter(activity, courseArrayLists, true, false, frag_type, CommonFragForList.this);
            commonListRV.setAdapter(adapter);
            errorTV.setVisibility(View.GONE);
            commonListRV.setVisibility(View.VISIBLE);
            if (frag_type.equals(Const.MYCART)) {
                initButton(courseArrayLists);
            }
        } else {
            if (frag_type.equals(Const.MYCART)) {
                initButton(courseArrayLists);
            }
            if (errorMessage.contains("Course not found.")) {
                errorTV.setText(getString(R.string.course_not_found));
            } else if (errorMessage.contains("Something went wrong")) {
//                if (frag_type.equals(Const.ALLCOURSES))
//                    Helper.showErrorLayoutForNav(null, activity, 1, 2);
//                else
//                    Helper.showErrorLayoutForNoNav(null, activity, 1, 2);
////                Helper.showErrorLayoutForNoNav(apiType, activity, 1, 2);
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                errorTV.setText(errorMessage);
            }
            errorTV.setVisibility(View.VISIBLE);
            commonListRV.setVisibility(View.GONE);
        }
    }

    private void InitSearchCourseAdapter() {
        if (TextUtils.isEmpty(last_course_id)) {
            if (searchArrayList.size() > 0) {
//                courseListAdapter = new CourseListAdapter(activity, searchArrayList, Const.SEEALL_COURSE);
                adapter = new IBTPracticeViewAllAdapter(activity, searchArrayList);
                commonListRV.setAdapter(adapter);
                errorTV.setVisibility(View.GONE);
                commonListRV.setVisibility(View.VISIBLE);
            } else {
                errorTV.setText(errorMessage);
                errorTV.setVisibility(View.VISIBLE);
                commonListRV.setVisibility(View.GONE);
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        errorMessage = jsonstring;
        apiType = apitype;

        switch (apitype) {
            case API.API_GET_MY_COURSE_DATA:
                InitMyCourseAdapter();
                break;
            case API.API_GET_FAQ_DATA:
                InitFaqAdapter();
                break;
            case API.API_GET_ALL_CATEGORY_DATA:
                InitMyCourseAdapter();
                break;
            case API.API_GET_LANDING_PAGE_DATA:
                InitLandingPageAdapter();
                break;
            case API.API_GET_USER_GIVEN_TESTSERIES:
                InitMyTestSeriesAdapter();
                break;
            case API.API_SEARCH_COURSE:

                break;
        }

        try {
            if (jsonstring.equalsIgnoreCase(getResources().getString(R.string.internet_error_message))) {
                Toast.makeText(activity, getResources().getString(R.string.internet_error_message), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "ErrorCallBack: " + e.getMessage());
        }

    }

    @Override
    public void onMyCartDeleteClick(Courselist courselist) {
        CartItems cartItems = new CartItems(SharedPreference.getInstance().getLoggedInUser().getId(), courselist.getId());
        Helper.addToMyCartCourses(activity, cartItems, false, false, null);
        onResume();
    }
}


