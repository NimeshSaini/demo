package com.utkarshnew.android.Response;

import com.utkarshnew.android.Model.Tags;
import com.utkarshnew.android.Response.Registration.CoursesInterestedResponse;
import com.utkarshnew.android.Response.Registration.SpecializationResponse;
import com.utkarshnew.android.Response.Registration.StreamResponse;
import com.utkarshnew.android.Response.Registration.SubStreamResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar on 05-01-2018.
 */

public class MasterRegistrationResponse implements Serializable {

    private ArrayList<CoursesInterestedResponse> intersted_course;

    private ArrayList<SpecializationResponse> specialization;

    private ArrayList<SubStreamResponse> main_sub_category;

    private ArrayList<StreamResponse> main_category;

    private ArrayList<Tags> all_tags;

    public ArrayList<CoursesInterestedResponse> getIntersted_course() {
        return intersted_course;
    }

    public ArrayList<Tags> getAll_tags() {
        return all_tags;
    }


    @SerializedName("chrome_detection")
    @Expose
    private int chrome_detection;


    @SerializedName("recorder_detection")
    @Expose
    private int recorder_detection;

    @SerializedName("chromecast")
    @Expose
    private Chromecast chromecast;

    public int getChrome_detection() {
        return chrome_detection;
    }

    public void setChrome_detection(int chrome_detection) {
        this.chrome_detection = chrome_detection;
    }

    public void setAll_tags(ArrayList<Tags> all_tags) {
        this.all_tags = all_tags;
    }

    public void setIntersted_course(ArrayList<CoursesInterestedResponse> intersted_course) {
        this.intersted_course = intersted_course;
    }

    public ArrayList<SpecializationResponse> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(ArrayList<SpecializationResponse> specialization) {
        this.specialization = specialization;
    }

    public ArrayList<SubStreamResponse> getMain_sub_category() {
        return main_sub_category;
    }

    public void setMain_sub_category(ArrayList<SubStreamResponse> main_sub_category) {
        this.main_sub_category = main_sub_category;
    }

    public ArrayList<StreamResponse> getMain_category() {
        return main_category;
    }

    public void setMain_category(ArrayList<StreamResponse> main_category) {
        this.main_category = main_category;
    }

    public int getRecorder_detection() {
        return recorder_detection;
    }

    public void setRecorder_detection(int recorder_detection) {
        this.recorder_detection = recorder_detection;
    }

    public Chromecast getChromecast() {
        return chromecast;
    }

    public void setChromecast(Chromecast chromecast) {
        this.chromecast = chromecast;
    }

    @Override
    public String toString() {
        return "ClassPojo [intersted_course = " + intersted_course + ", specialization = " + specialization + ", main_sub_category = " + main_sub_category + ", main_category = " + main_category + "]";
    }

    public void setData(MasterRegistrationResponse masterRegistrationResponse) {
        setMain_category(masterRegistrationResponse.getMain_category());
        setMain_sub_category(masterRegistrationResponse.getMain_sub_category());
    }

    public class Chromecast {

        @SerializedName("reset_chromecast")
        @Expose
        private Integer resetChromecast;
        @SerializedName("chrome_cast")
        @Expose
        private List<String> chromeCast = null;

        public Integer getResetChromecast() {
            return resetChromecast;
        }

        public void setResetChromecast(Integer resetChromecast) {
            this.resetChromecast = resetChromecast;
        }

        public List<String> getChromeCast() {
            return chromeCast;
        }

        public void setChromeCast(List<String> chromeCast) {
            this.chromeCast = chromeCast;
        }


    }
}
