package com.utkarshnew.android.Utils.Network.retrofit;


import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.SharedPreference;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends MultiDexApplication {

    static String id;

    public static <S> S getRetrofitInstance(Class<S> serviceClass) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.MINUTES);


        if (SharedPreference.getInstance().getLoggedInUser() != null) {
            if (SharedPreference.getInstance().getLoggedInUser().getId() != null) {
                id = SharedPreference.getInstance().getLoggedInUser().getId();
            } else {
                id = "0";
            }
        } else {
            id = "0";
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(60, TimeUnit.MINUTES).readTimeout(60, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).build();

        final Gson gson0 = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson0))
                .client(client)
                .build();

        /*httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header(Const.USERID, id)
                        .header(Const.DEVICETOKEN, SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID))
                        .header(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ? SharedPreference.getInstance().getString(Const.JWT) : "")
                        .header(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)));
//                    .addQuery("?lang=", String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });*/

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(Const.USERID, id)
                        .addHeader(Const.DEVICETOKEN, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ?
                                SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID) : "utkarsh_device")
                        .addHeader(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                                SharedPreference.getInstance().getString(Const.JWT) : "")
                        .addHeader(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
                        .build();
                // Log.i("APP_OWNER_VALUE", Helper.getAppOwnerValue());
                return chain.proceed(request);
            }
        });


        return retrofit.create(serviceClass);
    }
}
