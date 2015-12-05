package com.mamay.freechat;

import android.app.Application;

import com.mamay.freechat.manager.LoginManager;

public class App extends Application{

    public static LoginManager loginManager;

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManager();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManager();
    }    public static LoginManager getLoginManager() {
        return loginManager;
    }
}
