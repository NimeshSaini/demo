package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhi on 11/11/2017.
 */

public class ResultTestSeries2 implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("template_id")
    @Expose
    private String templateId;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("is_reattempt")
    @Expose
    private String is_reattempt;
    @SerializedName("test_series_name")
    @Expose
    private String testSeriesName;
    @SerializedName("difficulty_level")
    @Expose
    private String difficultyLevel;
    @SerializedName("test_type")
    @Expose
    private String testType;
    @SerializedName("backend_user_id")
    @Expose
    private String backendUserId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;
    @SerializedName("time_in_mins")
    @Expose
    private String timeInMins;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("shuffle")
    @Expose
    private String shuffle;
    @SerializedName("answer_shuffle")
    @Expose
    private String answerShuffle;
    @SerializedName("time_boundation")
    @Expose
    private String timeBoundation;
    @SerializedName("pass_message")
    @Expose
    private String passMessage;
    @SerializedName("general_message")
    @Expose
    private String generalMessage;
    @SerializedName("fail_message")
    @Expose
    private String failMessage;
    @SerializedName("pass_percentage")
    @Expose
    private String passPercentage;
    @SerializedName("allow_duplicate_rank")
    @Expose
    private String allowDuplicateRank;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("result_date")
    @Expose
    private String resultDate;
    @SerializedName("publish")
    @Expose
    private String publish;
    @SerializedName("reward_points")
    @Expose
    private String rewardPoints;
    @SerializedName("set_type")
    @Expose
    private String setType;
    @SerializedName("stream")
    @Expose
    private String stream;
    @SerializedName("sub_stream")
    @Expose
    private String subStream;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("unit_id")
    @Expose
    private String unitId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("sub_topic_id")
    @Expose
    private String subTopicId;
    @SerializedName("lang_id")
    @Expose
    private String langId;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("is_calc_allowed")
    @Expose
    private String isCalcAllowed;

    public String getCutoff() {
        return cutoff;
    }

    public void setCutoff(String cutoff) {
        this.cutoff = cutoff;
    }

    @SerializedName("cutoff")
    @Expose
    private String cutoff;
    @SerializedName("watermark")
    @Expose
    private String watermark;
    @SerializedName("total_user_attempt")
    @Expose
    private Integer totalUserAttempt;
    @SerializedName("user_rank")
    @Expose
    private String userRank;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("best_score")
    @Expose
    private String bestScore;
    @SerializedName("avg_score")
    @Expose
    private String avgScore;
    @SerializedName("correct_count")
    @Expose
    private String correctCount;
    @SerializedName("incorrect_count")
    @Expose
    private String incorrectCount;
    @SerializedName("non_attempt")
    @Expose
    private String nonAttempt;
    @SerializedName("question_dump")
    @Expose
    private List<QuestionDumps> questionDump = null;

    public List<Questions2> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions2> questions) {
        this.questions = questions;
    }

    @SerializedName("questions")
    @Expose
    private List<Questions2> questions = null;

    public List<CutOff> getCut_off() {
        return cut_off;
    }

    public void setCut_off(List<CutOff> cut_off) {
        this.cut_off = cut_off;
    }

    @SerializedName("cut_off")
    @Expose
    private List<CutOff> cut_off = null;
    @SerializedName("percentile")
    @Expose
    private String percentile;
    @SerializedName("top_ten_list")
    @Expose
    private List<TopTenList> topTenList = null;
    @SerializedName("test_sections")
    @Expose
    private List<TestSections> testSections = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTestSeriesName() {
        return testSeriesName;
    }

    public void setTestSeriesName(String testSeriesName) {
        this.testSeriesName = testSeriesName;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getBackendUserId() {
        return backendUserId;
    }

    public void setBackendUserId(String backendUserId) {
        this.backendUserId = backendUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getShuffle() {
        return shuffle;
    }

    public void setShuffle(String shuffle) {
        this.shuffle = shuffle;
    }

    public String getAnswerShuffle() {
        return answerShuffle;
    }

    public void setAnswerShuffle(String answerShuffle) {
        this.answerShuffle = answerShuffle;
    }

    public String getTimeBoundation() {
        return timeBoundation;
    }

    public void setTimeBoundation(String timeBoundation) {
        this.timeBoundation = timeBoundation;
    }

    public String getPassMessage() {
        return passMessage;
    }

    public void setPassMessage(String passMessage) {
        this.passMessage = passMessage;
    }

    public String getGeneralMessage() {
        return generalMessage;
    }

    public void setGeneralMessage(String generalMessage) {
        this.generalMessage = generalMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    public String getAllowDuplicateRank() {
        return allowDuplicateRank;
    }

    public void setAllowDuplicateRank(String allowDuplicateRank) {
        this.allowDuplicateRank = allowDuplicateRank;
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

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(String rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSubStream() {
        return subStream;
    }

    public void setSubStream(String subStream) {
        this.subStream = subStream;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(String subTopicId) {
        this.subTopicId = subTopicId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIsCalcAllowed() {
        return isCalcAllowed;
    }

    public void setIsCalcAllowed(String isCalcAllowed) {
        this.isCalcAllowed = isCalcAllowed;
    }


    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public Integer getTotalUserAttempt() {
        return totalUserAttempt;
    }

    public void setTotalUserAttempt(Integer totalUserAttempt) {
        this.totalUserAttempt = totalUserAttempt;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getBestScore() {
        return bestScore;
    }

    public void setBestScore(String bestScore) {
        this.bestScore = bestScore;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
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

    public String getNonAttempt() {
        return nonAttempt;
    }

    public void setNonAttempt(String nonAttempt) {
        this.nonAttempt = nonAttempt;
    }

    public List<QuestionDumps> getQuestionDump() {
        return questionDump;
    }

    public void setQuestionDump(List<QuestionDumps> questionDump) {
        this.questionDump = questionDump;
    }

    /*  public List<CutOff> getCutOff() {
          return cutOff;
      }

      public void setCutOff(List<CutOff> cutOff) {
          this.cutOff = cutOff;
      }
  */
    public String getPercentile() {
        return percentile;
    }

    public void setPercentile(String percentile) {
        this.percentile = percentile;
    }

    public List<TopTenList> getTopTenList() {
        return topTenList;
    }

    public void setTopTenList(List<TopTenList> topTenList) {
        this.topTenList = topTenList;
    }

    public List<TestSections> getTestSections() {
        return testSections;
    }

    public void setTestSections(List<TestSections> testSections) {
        this.testSections = testSections;
    }

    public String getIs_reattempt() {
        return is_reattempt;
    }

    public void setIs_reattempt(String is_reattempt) {
        this.is_reattempt = is_reattempt;
    }


}
