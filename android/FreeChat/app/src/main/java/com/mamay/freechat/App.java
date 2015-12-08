package com.mamay.freechat;

import android.app.Application;
import android.graphics.Typeface;

import com.mamay.freechat.manager.LoginManager;

/**
 * Application specification for the FreeChat.
 */
public class App extends Application{

    /**
     * Log in manager instance, that helps the app to know the log in status of the user.
     */
    private static LoginManager loginManager;
    private static Typeface chatFont;

    public static LoginManager getLoginManager() {
        return loginManager;
    }

    public static Typeface getChatFont() {
        return chatFont;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManager();
        chatFont = Typeface.createFromAsset(getAssets(), "chatfont.ttf");
    }
}
