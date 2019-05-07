package com.kaoyan.kaoyandemo.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("register")
    Call<Object> register(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("kaoyanList")
    Call<Object> kaoyanList(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("setPost")
    Call<Object> setPost(@Field("vars") String vars);
}
