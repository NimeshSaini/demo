package com.utkarshnew.android.home.livetest;


import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.makeramen.roundedimageview.RoundedImageView;
import com.utkarshnew.android.home.liveclasses.PayloadData;
import com.utkarshnew.android.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LiveTestData implements Serializable {

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @SerializedName("course_name")
    @Expose
    private String course_name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("description_2")
    @Expose
    private String description2;
    @SerializedName("test_series_name")
    @Expose
    private String testSeriesName;
    @SerializedName("test_code")
    @Expose
    private String testCode;
    @SerializedName("test_type")
    @Expose
    private String testType;
    @SerializedName("set_type")
    @Expose
    private String setType;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("lang_id")
    @Expose
    private String langId;
    @SerializedName("is_locked")
    @Expose
    private String isLocked;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;
    @SerializedName("time_in_mins")
    @Expose
    private String timeInMins;
    @SerializedName("report_id")
    @Expose
    private String reportId;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("correct_count")
    @Expose
    private String correctCount;

    public String getIs_reattempt() {
        return is_reattempt;
    }

    public void setIs_reattempt(String is_reattempt) {
        this.is_reattempt = is_reattempt;
    }

    @SerializedName("is_reattempt")
    @Expose
    private String is_reattempt;
    @SerializedName("incorrect_count")
    @Expose
    private String incorrectCount;
    @SerializedName("lang_used")
    @Expose
    private String langUsed;
    @SerializedName("result_date")
    @Expose
    private String resultDate;
    @SerializedName("course_id")
    @Expose
    private String courseId;

    private long cd_time;

    public long getCd_time() {
        return cd_time;
    }

    public void setCd_time(long cd_time) {
        this.cd_time = cd_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getTestSeriesName() {
        return testSeriesName;
    }

    public void setTestSeriesName(String testSeriesName) {
        this.testSeriesName = testSeriesName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTimeInMins() {
        return timeInMins;
    }

    public void setTimeInMins(String timeInMins) {
        this.timeInMins = timeInMins;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(String correctCount) {
        this.correctCount = correctCount;
    }

    public String getIncorrectCount() {
        return incorrectCount;
    }

    public void setIncorrectCount(String incorrectCount) {
        this.incorrectCount = incorrectCount;
    }

    public String getLangUsed() {
        return langUsed;
    }

    public void setLangUsed(String langUsed) {
        this.langUsed = langUsed;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public PayloadData getPayload() {
        return payload;
    }

    public void setPayload(PayloadData payload) {
        this.payload = payload;
    }

    @SerializedName("payload")
    @Expose
    private PayloadData payload;

    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    @SerializedName("submission_type")
    @Expose
    private String submission_type;


  /*  @SuppressLint("CheckResult")
    @BindingAdapter("linkimage")
    public static    void  loadImage(RoundedImageView imageView, String url){
            Glide.with(imageView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.square_thumbnail).error(R.drawable.square_thumbnail));
    }
*/

    @BindingAdapter("time")
    public static void  time(TextView textView, LiveTestData liveTestData){
        String time="";
           time = getDate(Long.parseLong(liveTestData.getStartDate())*1000, "dd-MMM-yyyy hh:mm a");
                time = time+" - "+getDate(Long.parseLong(liveTestData.getEndDate())*1000, "dd-MMM-yyyy hh:mm a");
        textView.setText(time);
    }

    private static String getDate(long start_msecond, String dateFormat) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat);
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(start_msecond);
        return simpleDateFormat.format(calendar.getTime());
    }


}

