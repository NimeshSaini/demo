package com.utkarshnew.android.courses.Interfaces;


import java.io.File;

public interface AsyncTaskCompleteListener {

    void onTaskComplete(String inputStream, File filepath);
}