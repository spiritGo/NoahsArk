package com.example.spirit.noahsark.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by spirit on 2018/3/18.
 */

public class SpUtils {

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences("info.txt", Context
                .MODE_PRIVATE);
        return sp.getInt(key, value);
    }
}
