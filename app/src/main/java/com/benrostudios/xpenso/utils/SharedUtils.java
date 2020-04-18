package com.benrostudios.xpenso.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    Context mContext;
    public static final String UID = "uid";
    public static final String FIRST_TIME = "first_time";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedUtils(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveCount(String uid) {
        editor.putString(UID, uid).commit();
    }

    public String retriveCount() {
        return sharedPreferences.getString(UID, null);
    }

    public void saveFirstTime(Boolean isFirstTime) {
        editor.putBoolean(FIRST_TIME, isFirstTime).commit();
    }

    public Boolean retriveFirstTime() {
        return sharedPreferences.getBoolean(FIRST_TIME, true);
    }
}
