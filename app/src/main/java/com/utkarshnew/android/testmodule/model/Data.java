
package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    @SerializedName("user_details")
    @Expose
    private UserInfo userDetails;
    @SerializedName("test_basic")
    @Expose
    private TestBasic testBasic;
    @SerializedName("test_sections")
    @Expose
    private List<TestSection> testSections = null;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("questions_hindi")
    @Expose
    private List<Question> questionsHindi = null;


    public List<Questions2> getQuestion_response() {
        return question_response;
    }

    public void setQuestion_response(List<Questions2> question_response) {
        this.question_response = question_response;
    }

    List<Questions2> question_response;


    public ResumeDump getResume_dump() {
        return resume_dump;
    }

    public void setResume_dump(ResumeDump resume_dump) {
        this.resume_dump = resume_dump;
    }

    @Expose
    private ResumeDump resume_dump = null;

    //    @SerializedName("active_ques")
//    @Expose
    private String activeQues;
    //    @SerializedName("question_dump")
//    @Expose
    private List<QuestionDump> questionDump = null;

    public String getActiveQues() {
        return activeQues;
    }

    public void setActiveQues(String activeQues) {
        this.activeQues = activeQues;
    }


    public UserInfo getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserInfo userDetails) {
        this.userDetails = userDetails;
    }

    public TestBasic getTestBasic() {
        return testBasic;
    }

    public void setTestBasic(TestBasic testBasic) {
        this.testBasic = testBasic;
    }

    public List<TestSection> getTestSections() {
        return testSections;
    }

    public void setTestSections(List<TestSection> testSections) {
        this.testSections = testSections;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestionsHindi() {
        return questionsHindi;
    }

    public void setQuestionsHindi(List<Question> questionsHindi) {
        this.questionsHindi = questionsHindi;
    }

    public List<QuestionDump> getQuestionDump() {
        return questionDump;
    }


}