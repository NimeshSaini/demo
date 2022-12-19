# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes *Annotation*

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes InnerClasses
-keep class **R
-keep class **.R$* {
    <fields>;
}

-optimizations !method/removal/parameter
-keepattributes *Annotation*, Signature, Exception
-keepattributes SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile


-keep class com.utkarshnew.android.EncryptionModel.EncryptionData { *; }
-keep class com.utkarshnew.android.EncryptionModel.LocationInfo { *; }
-keep class  com.utkarshnew.android.Model** { *; }
-keep class  com.utkarshnew.android.courses.modal** { *; }
-keep class  com.utkarshnew.android.CreateTest.Model** { *; }
-keep class  com.utkarshnew.android.helpChat.model** { *; }
-keep class  com.utkarshnew.android.pojo.Userinfo** { *; }
-keep class  com.utkarshnew.android.pojo.Userinfo.Data { *; }
-keep class  com.utkarshnew.android.pojo.Userinfo.UserData { *; }
-keep class  com.utkarshnew.android.table** { *; }
-keep class  com.utkarshnew.android.home.model** { *; }
-keep class  com.utkarshnew.android.Login.Pojo** { *; }
-keep class  com.utkarshnew.android.home.liveclasses** { *; }
-keep class  com.utkarshnew.android.home.livetest** { *; }
-keep class androidx.core.app.CoreComponentFactory { *; }
-keep class com.utkarshnew.android.Response**{ *; }
-keep class com.utkarshnew.android.purchasehistory.model** { *; }
-keep class com.utkarshnew.android.Webview.Model** { *; }
-keep class com.utkarshnew.android.testmodule.model** { *; }
-keep class com.utkarshnew.android.Coupon.Models** { *; }
-keep class com.utkarshnew.android.Intro** { *; }
-keep class  com.utkarshnew.android.feeds** { *; }
-keep class com.utkarshnew.android.address** { *; }

-keep class com.naveed.ytextractor** {*;}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}


-keep class com.shockwave**{ *; }

# Class names are needed in reflection
-keepnames class com.amazonaws** {*;}
-keepnames class com.amazon** {*;}
-keep class com.amazonaws.services.**.*Handler
-dontwarn org.apache.http.**
-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**


# PallyCon
-keep class com.pallycon.** {*;}
-keep class com.google.android.exoplayer2.** {*;}
-keep class net.sqlcipher.** {*; }
-keep class com.whitecryption.skb.** {*;}


