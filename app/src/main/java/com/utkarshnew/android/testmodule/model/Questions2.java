package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Questions2 implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("config_id")
    @Expose
    private String configId;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("marks_per_question")
    @Expose
    private String marksPerQuestion;
    @SerializedName("is_partial_marking")
    @Expose
    private String isPartialMarking;

    public String getParagraph_text() {
        return paragraph_text;
    }

    public void setParagraph_text(String paragraph_text) {
        this.paragraph_text = paragraph_text;
    }

    @SerializedName("paragraph_text")
    @Expose
    private String paragraph_text;
    @SerializedName("negative_marks")
    @Expose
    private String negativeMarks;
    @SerializedName("section_cutoff")
    @Expose
    private String sectionCutoff;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("option_2")
    @Expose
    private String option2;
    @SerializedName("option_3")
    @Expose
    private String option3;
    @SerializedName("option_4")
    @Expose
    private String option4;
    @SerializedName("option_5")
    @Expose
    private String option5;
    @SerializedName("option_6")
    @Expose
    private String option6;
    @SerializedName("option_7")
    @Expose
    private String option7;
    @SerializedName("option_8")
    @Expose
    private String option8;
    @SerializedName("option_9")
    @Expose
    private String option9;
    @SerializedName("option_10")
    @Expose
    private String option10;
    @SerializedName("is_correct")
    @Expose
    private Integer isCorrect;

    public String getSolution_url() {
        return solution_url;
    }

    public void setSolution_url(String solution_url) {
        this.solution_url = solution_url;
    }

    @SerializedName("solution_url")
    @Expose
    private String solution_url;

    public Questions2() {
    }

    public Questions2(String id, String question, String configId, String answer, String questionType, String option1, String option2, String option3, String option4, String option5) {
        this.id = id;
        this.question = question;
        this.configId = configId;
        this.answer = answer;
        this.questionType = questionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    private String right_answer;

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    @SerializedName("is_bookmarked")
    @Expose
    private String is_bookmarked;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getMarksPerQuestion() {
        return marksPerQuestion;
    }

    public void setMarksPerQuestion(String marksPerQuestion) {
        this.marksPerQuestion = marksPerQuestion;
    }

    public String getIsPartialMarking() {
        return isPartialMarking;
    }

    public void setIsPartialMarking(String isPartialMarking) {
        this.isPartialMarking = isPartialMarking;
    }

    public String getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(String negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public String getSectionCutoff() {
        return sectionCutoff;
    }

    public void setSectionCutoff(String sectionCutoff) {
        this.sectionCutoff = sectionCutoff;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getOption6() {
        return option6;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public String getOption7() {
        return option7;
    }

    public void setOption7(String option7) {
        this.option7 = option7;
    }

    public String getOption8() {
        return option8;
    }

    public void setOption8(String option8) {
        this.option8 = option8;
    }

    public String getOption9() {
        return option9;
    }

    public void setOption9(String option9) {
        this.option9 = option9;
    }

    public String getOption10() {
        return option10;
    }

    public void setOption10(String option10) {
        this.option10 = option10;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

}