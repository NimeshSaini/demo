package com.utkarshnew.android.Utils.Network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit != null)
            return retrofit;
/*

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
//                        .addHeader(Const.USERID, Helper.isStringValid(SharedPreference.getInstance().getLoggedInUser().getId()) ?
//                                SharedPreference.getInstance().getLoggedInUser().getId() : "")
                        .addHeader(Const.USERID, SharedPreference.getInstance().getLoggedInUser().getId())
                        .addHeader(Const.DEVICETOKEN, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ?
                                SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID) : "")
                        .addHeader(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                                SharedPreference.getInstance().getString(Const.JWT) : "")
                        .addHeader(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
//                        .addHeader(Const.DEVICE, Const.A)
                        .build();
                return chain.proceed(request);
            }
        }).connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
*/


        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }


    public static Retrofit getClientV2() {
        if (retrofit != null)
            return retrofit;
        retrofit = new Retrofit.Builder().baseUrl("http://www.online.utkarsh.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


/*    public static Retrofit getRetrofitInstance() {

        if (retrofit != null)
            return retrofit;

        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .client(httpClient.build()).build();

        return retrofit;
    }*/
}
