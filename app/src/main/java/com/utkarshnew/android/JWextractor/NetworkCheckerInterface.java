package com.utkarshnew.android.JWextractor;

public interface NetworkCheckerInterface {
    void continueExecution();

    default void onUnstableInternet() {
    }

    void cancelExecution();
}
