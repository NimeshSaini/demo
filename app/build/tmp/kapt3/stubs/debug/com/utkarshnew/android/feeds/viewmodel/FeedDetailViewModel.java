package com.utkarshnew.android.feeds.viewmodel;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0013H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010BJ\u0019\u0010C\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010BJ\u0006\u0010D\u001a\u00020@J\u0006\u0010E\u001a\u00020@J\b\u0010F\u001a\u00020@H\u0014R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\f\"\u0004\b\u0015\u0010\u0011R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u0011R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u0011R\u0011\u0010 \u001a\u00020!\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R \u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010\u0011R\u001d\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R \u0010/\u001a\b\u0012\u0004\u0012\u00020\u000e0(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010*\"\u0004\b1\u00102R \u00103\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\f\"\u0004\b5\u0010\u0011R\u001a\u00106\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006G"}, d2 = {"Lcom/utkarshnew/android/feeds/viewmodel/FeedDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/appsquadz/mvvmwithretrofit/repository/Repository;", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "(Lcom/appsquadz/mvvmwithretrofit/repository/Repository;Lcom/utkarshnew/android/Room/UtkashRoom;)V", "_relatedCourse", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/utkarshnew/android/feeds/dataclass/NewCourseData;", "get_relatedCourse", "()Landroidx/lifecycle/MutableLiveData;", "adapter_bodydata", "", "getAdapter_bodydata", "setAdapter_bodydata", "(Landroidx/lifecycle/MutableLiveData;)V", "adapter_response", "Lorg/json/JSONObject;", "getAdapter_response", "setAdapter_response", "bodydata", "getBodydata", "setBodydata", "handler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "getHandler", "()Lkotlinx/coroutines/CoroutineExceptionHandler;", "jsonObjectmutable", "getJsonObjectmutable", "setJsonObjectmutable", "metaindexencryptionData", "Lcom/utkarshnew/android/EncryptionModel/EncryptionData;", "getMetaindexencryptionData", "()Lcom/utkarshnew/android/EncryptionModel/EncryptionData;", "progressvalue", "getProgressvalue", "setProgressvalue", "relatedCourse", "Landroidx/lifecycle/LiveData;", "getRelatedCourse", "()Landroidx/lifecycle/LiveData;", "getRepository", "()Lcom/appsquadz/mvvmwithretrofit/repository/Repository;", "setRepository", "(Lcom/appsquadz/mvvmwithretrofit/repository/Repository;)V", "responseString", "getResponseString", "setResponseString", "(Landroidx/lifecycle/LiveData;)V", "type", "getType", "setType", "userid", "getUserid", "()Ljava/lang/String;", "setUserid", "(Ljava/lang/String;)V", "getUtkashRoom", "()Lcom/utkarshnew/android/Room/UtkashRoom;", "setUtkashRoom", "(Lcom/utkarshnew/android/Room/UtkashRoom;)V", "checkandupdateversion", "", "jsonObject", "(Lorg/json/JSONObject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkchnageDetector", "getPostData", "getcourutine_adapter_post", "onCleared", "app_debug"})
public final class FeedDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private com.appsquadz.mvvmwithretrofit.repository.Repository repository;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<org.json.JSONObject> adapter_response;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<org.json.JSONObject> jsonObjectmutable;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> adapter_bodydata;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> progressvalue;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> type;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> bodydata;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData>> _relatedCourse = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData>> relatedCourse = null;
    @org.jetbrains.annotations.NotNull()
    private final com.utkarshnew.android.EncryptionModel.EncryptionData metaindexencryptionData = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String userid = "";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineExceptionHandler handler = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.LiveData<java.lang.String> responseString;
    
    @javax.inject.Inject()
    public FeedDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.appsquadz.mvvmwithretrofit.repository.Repository repository, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Room.UtkashRoom utkashRoom) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appsquadz.mvvmwithretrofit.repository.Repository getRepository() {
        return null;
    }
    
    public final void setRepository(@org.jetbrains.annotations.NotNull()
    com.appsquadz.mvvmwithretrofit.repository.Repository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Room.UtkashRoom getUtkashRoom() {
        return null;
    }
    
    public final void setUtkashRoom(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Room.UtkashRoom p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<org.json.JSONObject> getAdapter_response() {
        return null;
    }
    
    public final void setAdapter_response(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<org.json.JSONObject> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<org.json.JSONObject> getJsonObjectmutable() {
        return null;
    }
    
    public final void setJsonObjectmutable(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<org.json.JSONObject> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getAdapter_bodydata() {
        return null;
    }
    
    public final void setAdapter_bodydata(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getProgressvalue() {
        return null;
    }
    
    public final void setProgressvalue(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getType() {
        return null;
    }
    
    public final void setType(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getBodydata() {
        return null;
    }
    
    public final void setBodydata(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData>> get_relatedCourse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData>> getRelatedCourse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.EncryptionModel.EncryptionData getMetaindexencryptionData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserid() {
        return null;
    }
    
    public final void setUserid(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.CoroutineExceptionHandler getHandler() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getResponseString() {
        return null;
    }
    
    public final void setResponseString(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<java.lang.String> p0) {
    }
    
    public final void getcourutine_adapter_post() {
    }
    
    public final void getPostData() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkchnageDetector(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject jsonObject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.Object checkandupdateversion(org.json.JSONObject jsonObject, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}