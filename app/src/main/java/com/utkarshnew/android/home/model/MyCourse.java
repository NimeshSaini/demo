package com.utkarshnew.android.home.model;

import com.utkarshnew.android.Model.Courselist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCourse implements Serializable {


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


    private boolean status;
    private String message;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    public ArrayList<Courselist> getData() {
        return data;
    }

    public void setData(ArrayList<Courselist> data) {
        this.data = data;
    }

    private ArrayList<Courselist> data;
    private List<Courselist> paid_course;
    private List<Courselist> freecourse;
    private ArrayList<Courselist> batchcourse;


    public List<Courselist> getPaid_course() {
        return paid_course;
    }

    public void setPaid_course(List<Courselist> paid_course) {
        this.paid_course = paid_course;
    }

    public List<Courselist> getFreecourse() {
        return freecourse;
    }

    public void setFreecourse(List<Courselist> freecourse) {
        this.freecourse = freecourse;
    }

    public ArrayList<Courselist> getBatchcourse() {
        return batchcourse;
    }

    public void setBatchcourse(ArrayList<Courselist> batchcourse) {
        this.batchcourse = batchcourse;
    }





   /* public class Data implements Serializable{







        public ArrayList<Courselist> getCourses() {
            return courses;
        }

        public void setCourses(ArrayList<Courselist> courses) {
            this.courses = courses;
        }

        private ArrayList<Courselist> courses;

        public ArrayList<Courselist> getPaid_course() {
            return paid_course;
        }

        public void setPaid_course(ArrayList<Courselist> paid_course) {
            this.paid_course = paid_course;
        }

        public ArrayList<Courselist> getFreecourse() {
            return freecourse;
        }

        public void setFreecourse(ArrayList<Courselist> freecourse) {
            this.freecourse = freecourse;
        }

        public ArrayList<Courselist> getBatchcourse() {
            return batchcourse;
        }

        public void setBatchcourse(ArrayList<Courselist> batchcourse) {
            this.batchcourse = batchcourse;
        }




    }*/


}