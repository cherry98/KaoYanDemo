package com.kaoyan.kaoyandemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    private static final String USER = "user";

    public static void setLoggedStatus(Context context, boolean isLogged) {//存储登录状态
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }

    public static String getUserId(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getString("userId", "");
    }

    public static void setUserId(Context context, String userId) {//存储用户的userId 是每个用户的唯一标识
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getBoolean("isLogged", false);
    }

}
