
package com.utkarshnew.android.Model.TestPDFData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestJson implements Serializable {

    @SerializedName("subjective")
    @Expose
    private Subjective subjective;
    @SerializedName("objective")
    @Expose
    private Objective objective;
    @SerializedName("quiz")
    @Expose
    private Quiz quiz;
    @SerializedName("pdf")
    @Expose
    private PDFJson pdf;
    @SerializedName("bookmark")
    @Expose
    private BICButton bookmark;
    @SerializedName("index")
    @Expose
    private BICButton index;
    @SerializedName("chat")
    @Expose
    private BICButton chat;

    public Subjective getSubjective() {
        return subjective;
    }

    public void setSubjective(Subjective subjective) {
        this.subjective = subjective;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public PDFJson getPdf() {
        return pdf;
    }

    public void setPdf(PDFJson pdf) {
        this.pdf = pdf;
    }

    public BICButton getBookmark() {
        return bookmark;
    }

    public void setBookmark(BICButton bookmark) {
        this.bookmark = bookmark;
    }

    public BICButton getIndex() {
        return index;
    }

    public void setIndex(BICButton index) {
        this.index = index;
    }

    public BICButton getChat() {
        return chat;
    }

    public void setChat(BICButton chat) {
        this.chat = chat;
    }
}
