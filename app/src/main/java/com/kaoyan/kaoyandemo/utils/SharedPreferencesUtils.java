package com.kaoyan.kaoyandemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    private static final String USER = "user";

    public static void setLoggedStatus(Context context, boolean isLogged) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }

    public static String getUserId(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getString("userId", "");
    }

    public static void setUserId(Context context, String userId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getBoolean("isLogged", false);
    }

    public static void setAttentionSchool(Context context, String name) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("schoolName", name);
        editor.apply();
    }

    public static String getAttentionSchoolName(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getString("schoolName", "");
    }
}
