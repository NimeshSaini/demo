package com.utkarshnew.android.home.model;

/**
 * Created by root on 19/3/18 10:12 AM.
 */

public class LibUsedModel {

    public LibUsedModel() {
    }

    private String[] libName = {"Razorpay", "gson", "Glide", "Custom Activity On Crash", "Circle Image View", "Android Debug DB", "Joda Time", "Material Intro Screen", "AVLoadingIndicatorView", "Sticky Headers", "Facebook Shimmer", "android-crop", "Drift"};

    private String[] libDesc = {"The easiest way to accept, process and disburse digital payments for businesses in India. \nhttps://razorpay.com/", "A Java serialization/deserialization library to convert Java Objects into JSON and back. \nhttps://github.com/google/gson", "An image loading and caching library for Android focused on smooth scrolling. \nhttps://github.com/bumptech/glide", "Android library that allows launching a custom activity when your app crashes, instead of showing the hated \"Unfortunately, X has stopped\" dialog. \nhttps://github.com/Ereza/CustomActivityOnCrash", "A circular ImageView for Android. \nhttps://github.com/hdodenhof/CircleImageView", "A library for debugging android databases and shared preferences - Make Debugging Great Again. \nhttps://github.com/amitshekhariitbhu/Android-Debug-Database", "Joda-Time is the widely used replacement for the Java date and time classes prior to Java SE 8. \nhttps://github.com/JodaOrg/joda-time", "Inspired by Heinrich Reimer Material Intro and developed with love from scratch. \nhttps://github.com/TangoAgency/material-intro-screen", "Nice loading animations for Android. \nhttps://github.com/8183780/AVLoadingIndicatorView", "\n" +
            "Adapter and LayoutManager for Android RecyclerView which enables sticky header positioning. \nhttps://github.com/ShamylZakariya/StickyHeaders", "An easy, flexible way to add a shimmering effect to any view in an Android app. \nhttps://github.com/facebook/shimmer-android", "\n" +
            "Android library project for cropping images. \nhttps://github.com/jdamcd/android-crop", "\n" +
            "The Drift Android SDK. \nhttps://github.com/Driftt/drift-sdk-android"};

    public String[] getLibName() {
        return libName;
    }

    public void setLibName(String[] libName) {
        this.libName = libName;
    }

    public String[] getLibDesc() {
        return libDesc;
    }

    public void setLibDesc(String[] libDesc) {
        this.libDesc = libDesc;
    }
}
