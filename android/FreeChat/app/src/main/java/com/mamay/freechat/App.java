package com.mamay.freechat;

import android.app.Application;

import com.mamay.freechat.manager.LoginManager;

/**
 * Application specification for the FreeChat.
 */
public class App extends Application{

    /**
     * Log in manager instance, that helps the app to know the log in status of the user.
     */
    public static LoginManager loginManager;

    public static LoginManager getLoginManager() {
        return loginManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManager();
    }
}
