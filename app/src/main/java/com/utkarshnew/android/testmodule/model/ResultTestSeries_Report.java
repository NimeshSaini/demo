package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultTestSeries_Report implements Serializable {

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getIs_android_price() {
        return is_android_price;
    }

    public void setIs_android_price(String is_android_price) {
        this.is_android_price = is_android_price;
    }

    public ArrayList<Errors> getError() {
        return error;
    }

    public void setError(ArrayList<Errors> error) {
        this.error = error;
    }
    private String message;
    private DataResult data;
    public DataResult getData() {
        return data;
    }
    public void setData(DataResult data) {
        this.data = data;
    }
    private String is_android_price;
    private ArrayList<Errors> error;
    public  class  DataResult implements  Serializable
    {

        @SerializedName("questions_hindi")
        @Expose
        private ArrayList<Questions2> questionsHindi = null;
        private String id;
        private String template_id;
        private String image;
        private String test_series_name;
        private String difficulty_level;
        private String test_type;
        private String backend_user_id;
        private String teacher_id;
        private String description;
        private String session;
        private String total_questions;
        private String time_in_mins;
        private String total_marks;
        private String shuffle;
        private String answer_shuffle;
        private String time_boundation;
        private String pass_message;
        private String general_message;
        private String fail_message;
        private String pass_percentage;
        private String allow_duplicate_rank;
        private String start_date;
        private String end_date;
        private String result_date;
        private String publish;
        private String reward_points;
        private String set_type;
        private String stream;
        private String sub_stream;
        private String subject;
        private String unit_id;
        private String chapter_id;
        private String topic_id;
        private String sub_topic_id;
        private String lang_id;
        private String video_url;
        private String is_calc_allowed;
        private String feedback_allowed;
        private String is_reattempt;
        private String cutoff;
        private String watermark;
        private String multiple_video_title;
        private String multiple_video;
        private String total_user_attempt;
        private String user_rank;
        private String marks;
        private String best_score;
        private String avg_score;
        private String correct_count;
        private String incorrect_count;
        private String non_attempt;
        private String time_remain;

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        private String percentage;

        ArrayList<QuestionDumps> question_dump;
        ArrayList<CutOff> cut_off;
        private String percentile;
        ArrayList<TopTenList> top_ten_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTest_series_name() {
            return test_series_name;
        }

        public void setTest_series_name(String test_series_name) {
            this.test_series_name = test_series_name;
        }

        public String getDifficulty_level() {
            return difficulty_level;
        }

        public void setDifficulty_level(String difficulty_level) {
            this.difficulty_level = difficulty_level;
        }

        public String getTest_type() {
            return test_type;
        }

        public void setTest_type(String test_type) {
            this.test_type = test_type;
        }

        public String getBackend_user_id() {
            return backend_user_id;
        }

        public void setBackend_user_id(String backend_user_id) {
            this.backend_user_id = backend_user_id;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
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

        public String getTotal_questions() {
            return total_questions;
        }

        public void setTotal_questions(String total_questions) {
            this.total_questions = total_questions;
        }

        public String getTime_in_mins() {
            return time_in_mins;
        }

        public void setTime_in_mins(String time_in_mins) {
            this.time_in_mins = time_in_mins;
        }

        public String getTotal_marks() {
            return total_marks;
        }

        public void setTotal_marks(String total_marks) {
            this.total_marks = total_marks;
        }

        public String getShuffle() {
            return shuffle;
        }

        public void setShuffle(String shuffle) {
            this.shuffle = shuffle;
        }

        public String getAnswer_shuffle() {
            return answer_shuffle;
        }

        public void setAnswer_shuffle(String answer_shuffle) {
            this.answer_shuffle = answer_shuffle;
        }

        public String getTime_boundation() {
            return time_boundation;
        }

        public void setTime_boundation(String time_boundation) {
            this.time_boundation = time_boundation;
        }

        public String getPass_message() {
            return pass_message;
        }

        public void setPass_message(String pass_message) {
            this.pass_message = pass_message;
        }

        public String getGeneral_message() {
            return general_message;
        }

        public void setGeneral_message(String general_message) {
            this.general_message = general_message;
        }

        public String getFail_message() {
            return fail_message;
        }

        public void setFail_message(String fail_message) {
            this.fail_message = fail_message;
        }

        public String getPass_percentage() {
            return pass_percentage;
        }

        public void setPass_percentage(String pass_percentage) {
            this.pass_percentage = pass_percentage;
        }

        public String getAllow_duplicate_rank() {
            return allow_duplicate_rank;
        }

        public void setAllow_duplicate_rank(String allow_duplicate_rank) {
            this.allow_duplicate_rank = allow_duplicate_rank;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getResult_date() {
            return result_date;
        }

        public void setResult_date(String result_date) {
            this.result_date = result_date;
        }

        public String getPublish() {
            return publish;
        }

        public void setPublish(String publish) {
            this.publish = publish;
        }

        public String getReward_points() {
            return reward_points;
        }

        public void setReward_points(String reward_points) {
            this.reward_points = reward_points;
        }

        public String getSet_type() {
            return set_type;
        }

        public void setSet_type(String set_type) {
            this.set_type = set_type;
        }

        public String getStream() {
            return stream;
        }

        public void setStream(String stream) {
            this.stream = stream;
        }

        public String getSub_stream() {
            return sub_stream;
        }

        public void setSub_stream(String sub_stream) {
            this.sub_stream = sub_stream;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getSub_topic_id() {
            return sub_topic_id;
        }

        public void setSub_topic_id(String sub_topic_id) {
            this.sub_topic_id = sub_topic_id;
        }

        public String getLang_id() {
            return lang_id;
        }

        public void setLang_id(String lang_id) {
            this.lang_id = lang_id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getIs_calc_allowed() {
            return is_calc_allowed;
        }

        public void setIs_calc_allowed(String is_calc_allowed) {
            this.is_calc_allowed = is_calc_allowed;
        }

        public String getFeedback_allowed() {
            return feedback_allowed;
        }

        public void setFeedback_allowed(String feedback_allowed) {
            this.feedback_allowed = feedback_allowed;
        }

        public String getIs_reattempt() {
            return is_reattempt;
        }

        public void setIs_reattempt(String is_reattempt) {
            this.is_reattempt = is_reattempt;
        }

        public String getCutoff() {
            return cutoff;
        }

        public void setCutoff(String cutoff) {
            this.cutoff = cutoff;
        }

        public String getWatermark() {
            return watermark;
        }

        public void setWatermark(String watermark) {
            this.watermark = watermark;
        }

        public String getMultiple_video_title() {
            return multiple_video_title;
        }

        public void setMultiple_video_title(String multiple_video_title) {
            this.multiple_video_title = multiple_video_title;
        }

        public String getMultiple_video() {
            return multiple_video;
        }

        public void setMultiple_video(String multiple_video) {
            this.multiple_video = multiple_video;
        }

        public String getTotal_user_attempt() {
            return total_user_attempt;
        }

        public void setTotal_user_attempt(String total_user_attempt) {
            this.total_user_attempt = total_user_attempt;
        }

        public String getUser_rank() {
            return user_rank;
        }

        public void setUser_rank(String user_rank) {
            this.user_rank = user_rank;
        }

        public String getMarks() {
            return marks;
        }

        public void setMarks(String marks) {
            this.marks = marks;
        }

        public String getBest_score() {
            return best_score;
        }

        public void setBest_score(String best_score) {
            this.best_score = best_score;
        }

        public String getAvg_score() {
            return avg_score;
        }

        public void setAvg_score(String avg_score) {
            this.avg_score = avg_score;
        }

        public String getCorrect_count() {
            return correct_count;
        }

        public void setCorrect_count(String correct_count) {
            this.correct_count = correct_count;
        }

        public String getIncorrect_count() {
            return incorrect_count;
        }

        public void setIncorrect_count(String incorrect_count) {
            this.incorrect_count = incorrect_count;
        }

        public String getNon_attempt() {
            return non_attempt;
        }

        public void setNon_attempt(String non_attempt) {
            this.non_attempt = non_attempt;
        }

        public String getTime_remain() {
            return time_remain;
        }

        public void setTime_remain(String time_remain) {
            this.time_remain = time_remain;
        }

        public ArrayList<QuestionDumps> getQuestion_dump() {
            return question_dump;
        }

        public void setQuestion_dump(ArrayList<QuestionDumps> question_dump) {
            this.question_dump = question_dump;
        }

        public ArrayList<CutOff> getCut_off() {
            return cut_off;
        }

        public void setCut_off(ArrayList<CutOff> cut_off) {
            this.cut_off = cut_off;
        }

        public String getPercentile() {
            return percentile;
        }

        public void setPercentile(String percentile) {
            this.percentile = percentile;
        }

        public ArrayList<TopTenList> getTop_ten_list() {
            return top_ten_list;
        }

        public void setTop_ten_list(ArrayList<TopTenList> top_ten_list) {
            this.top_ten_list = top_ten_list;
        }

        public ArrayList<TestReportVideos> getTest_report_videos() {
            return test_report_videos;
        }

        public void setTest_report_videos(ArrayList<TestReportVideos> test_report_videos) {
            this.test_report_videos = test_report_videos;
        }

        public ArrayList<TestSections> getTest_sections() {
            return test_sections;
        }

        public void setTest_sections(ArrayList<TestSections> test_sections) {
            this.test_sections = test_sections;
        }

        public ArrayList<Questions2> getQuestionsHindi() {
            return questionsHindi;
        }

        public void setQuestionsHindi(ArrayList<Questions2> questionsHindi) {
            this.questionsHindi = questionsHindi;
        }

        public ArrayList<Questions2> getQuestions() {
            return questions;
        }

        public void setQuestions(ArrayList<Questions2> questions) {
            this.questions = questions;
        }

        ArrayList<TestReportVideos> test_report_videos;
        ArrayList<TestSections> test_sections;
        ArrayList<Questions2> questions;


    }
    public class Errors implements Serializable {

    }


}
