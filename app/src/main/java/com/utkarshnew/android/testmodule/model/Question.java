
package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    private String right_answer;

    public String getRight_answer_pos() {
        return right_answer_pos;
    }

    public void setRight_answer_pos(String right_answer_pos) {
        this.right_answer_pos = right_answer_pos;
    }

    private String right_answer_pos;

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    private String user_answer;


    public String getSolution_url() {
        return solution_url;
    }

    public void setSolution_url(String solution_url) {
        this.solution_url = solution_url;
    }

    @SerializedName("solution_url")
    @Expose
    private String solution_url;



    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("config_id")
    @Expose
    private String configId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_type")
    @Expose
    private String questionType;
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
    @SerializedName("paragraph_text")
    @Expose
    private String paragraphText;
    @SerializedName("description")
    @Expose
    private String description;
    //    @SerializedName("answer")
//    @Expose
    private String answer = "1";

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    private String is_bookmarked = "0";


    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    private boolean answered = false;

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    private int answerPosition = -1;

    public String getAnspositions() {
        return anspositions;
    }

    public void setAnspositions(String anspositions) {
        this.anspositions = anspositions;
    }

    private String anspositions = "-1";
    private ArrayList answers = new ArrayList();
    private int selectedanswerPosition = -1;

    private boolean iswronganswer = false;
    private int wronganswerPosition = -1;


    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    private String isCorrect="";
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    private boolean isPause = false;

    public int getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(int totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public ArrayList<mcSelection> selcted = new ArrayList();
    public ArrayList<mcSelection> selcted2 = new ArrayList();

    public void setSelcted(ArrayList<mcSelection> selcted) {
        this.selcted = selcted;

    }

    public ArrayList<mcSelection> getSelcted() {
        return selcted;
    }

    public void setSelcted2(ArrayList<mcSelection> selcted2) {
        this.selcted2 = selcted2;

    }

    public ArrayList<mcSelection> getSelcted2() {
        return selcted2;
    }

    private int totalTimeSpent = 0;
    private boolean isanswer = false;

    public boolean isanswer() {
        return isanswer;
    }

    public void setIsanswer(boolean isanswer, int position, String anspositions, ArrayList answers) {
        this.isanswer = isanswer;
        this.answerPosition = position;
        this.anspositions = anspositions;
        this.answers = answers;
    }

    public void setIsanswer(boolean isanswer, int position, String anspositions) {
        this.isanswer = isanswer;
        this.answerPosition = position;
        this.anspositions = anspositions;

    }

    public void setIsanswer(boolean isanswer, int position, ArrayList answers) {
        this.isanswer = isanswer;
        this.answerPosition = position;
        this.answers = answers;

    }

    public void setIsanswer(boolean isanswer, int position) {
        this.isanswer = isanswer;
        this.answerPosition = position;

    }

    public void setIswronganswer(boolean iswronganswer, int position) {
        this.iswronganswer = iswronganswer;
        this.wronganswerPosition = position;

    }

    public int getAnswerPosttion() {
        return answerPosition;
    }

    ArrayList<Integer> selectedValue = new ArrayList<>();

    public ArrayList<Integer> getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(ArrayList<Integer> selectedValue) {
        this.selectedValue = selectedValue;

    }

    ArrayList<String> selectedString = new ArrayList<>();

    public ArrayList<String> getSelectedString() {
        return selectedString;
    }

    public void setSelectedString(ArrayList<String> selectedString) {
        this.selectedString = selectedString;

    }

    public ArrayList getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList answers) {
        this.answers = answers;
    }

    public String getAnswerPosttions() {
        return anspositions;
    }

    public String getIsguess() {
        return isguess;
    }

    public void setIsguess(String isguess) {
        this.isguess = isguess;
    }

    private String isguess = "0";

    public boolean isIsanswerRight() {
        return isanswerRight;
    }

    public void setIsanswerRight(boolean isanswerRight) {
        this.isanswerRight = isanswerRight;
    }

    private boolean isanswerRight;

    public boolean isMarkForReview() {
        return isMarkForReview;
    }

    public void setMarkForReview(boolean markForReview) {
        isMarkForReview = markForReview;
    }

    public boolean isIssaveMarkForReview() {
        return issaveMarkForReview;
    }

    public void setIssaveMarkForReview(boolean issaveMarkForReview) {
        this.issaveMarkForReview = issaveMarkForReview;
    }

    private boolean isMarkForReview;
    private boolean issaveMarkForReview;

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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getColor() {
        if (isMarkForReview)
            return "4";
        else if (answerPosition == -1)
            return "3";
        else if (isanswer && answerPosition != -1)
            if (isanswerRight)
                return "1";
            else
                return "2";
        else
            return "3";

    }
}


