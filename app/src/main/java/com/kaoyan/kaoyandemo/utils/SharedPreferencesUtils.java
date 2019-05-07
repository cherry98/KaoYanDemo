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

    public static boolean getLoggedStatus(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getBoolean("isLogged", false);
    }

    public static void setAttentionSchool(Context context, String name, int num) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("schoolName", name);
        editor.putInt("schoolNum", num);
        editor.apply();
    }

    public static int getAttentionSchoolNum(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getInt("schoolNum", 0);
    }

    public static String getAttentionSchoolName(Context context) {
        return context.getSharedPreferences(USER, MODE_PRIVATE).getString("schoolName", "");
    }
}
