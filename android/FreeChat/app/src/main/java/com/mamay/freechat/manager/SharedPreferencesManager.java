package com.mamay.freechat.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.mamay.freechat.Const;

/**
 * Helps the app to access it's preferences.
 */
public class SharedPreferencesManager {

    private SharedPreferences sp;

    public SharedPreferencesManager(Context context) {
        sp = context.getSharedPreferences(Const.sharedprefs.SP_NAME, Context.MODE_PRIVATE);
    }

    public void write(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public void write(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public void write(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public void write(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public int readInt(String key) {
        return sp.getInt(key, 0);
    }

    public long readLong(String key) {
        return sp.getLong(key, 0l);
    }

    public boolean readBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public String readString(String key) {
        return sp.getString(key, "");
    }

}
