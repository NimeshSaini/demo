package com.utkarshnew.android.Webview.Model;

import java.io.Serializable;

public class QuestionArray implements Serializable {

    private String id;
    private String question;
    private String questionType;
    private String option;

    public QuestionArray(String id, String question, String questionType, String option) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.option = option;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
