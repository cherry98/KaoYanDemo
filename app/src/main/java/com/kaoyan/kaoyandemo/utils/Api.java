package com.kaoyan.kaoyandemo.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求调用接口
 */
public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("kaoyanList")
    Call<ResponseBody> kaoyanList(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("setPost")
    Call<ResponseBody> setPost(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("postList")
    Call<ResponseBody> postList(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("setCollect")
    Call<ResponseBody> setCollect(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("deleteCollect")
    Call<ResponseBody> deleteCollect(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("collectList")
    Call<ResponseBody> collectList(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("userData")
    Call<ResponseBody> userData(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("editUserData")
    Call<ResponseBody> editUserData(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("majorList")
    Call<ResponseBody> majorList(@Field("vars") String vars);

    @POST("schoolList")
    Call<ResponseBody> schoolList();

    @FormUrlEncoded
    @POST("setPostMessage")
    Call<ResponseBody> setPostMessage(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("messageList")
    Call<ResponseBody> messageList(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("getAllCollectSchool")
    Call<ResponseBody> getAllCollectSchool(@Field("vars") String vars);

    @FormUrlEncoded
    @POST("getAllCollectSchoolKaoyanData")
    Call<ResponseBody> getAllCollectSchoolKaoyanData(@Field("vars") String vars);
}