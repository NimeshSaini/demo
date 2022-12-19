package com.utkarshnew.android.CreateTest.Model;

import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.testmodule.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateTestQuesData implements Serializable {

    private boolean status;
    private String message;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    private  Data data;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public  class  Data {
        public ArrayList<Question> getData() {
            return data;
        }

        public void setData(ArrayList<Question> data) {
            this.data = data;
        }

        public ArrayList<Question> getQuestions_hindi() {
            return questions_hindi;
        }

        public void setQuestions_hindi(ArrayList<Question> questions_hindi) {
            this.questions_hindi = questions_hindi;
        }

        @SerializedName("questions")
        private ArrayList<Question> data;
        private ArrayList<Question> questions_hindi;

    }
}