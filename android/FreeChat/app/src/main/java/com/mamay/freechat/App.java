package com.mamay.freechat;

import android.app.Application;
import android.graphics.Typeface;

import com.mamay.freechat.manager.LoginManager;
import com.mamay.freechat.manager.NetworkManager;
import com.mamay.freechat.manager.SharedPreferencesManager;

/**
 * Application specification for the FreeChat.
 */
public class App extends Application{

    /**
     * Log in manager instance, that helps the app to know the log in status of the user.
     */
    private static LoginManager loginManager;
    private static Typeface chatFont;
    private static NetworkManager networkManager;
    private static SharedPreferencesManager sharedPreferencesManager;

    public static LoginManager getLoginManager() {
        return loginManager;
    }

    public static Typeface getChatFont() {
        return chatFont;
    }

    public static NetworkManager getNetworkManager() {
        return networkManager;
    }

    public static SharedPreferencesManager getSharedPreferencesManager() {
        return sharedPreferencesManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        chatFont = Typeface.createFromAsset(getAssets(), "chatfont.ttf");
        networkManager = new NetworkManager(this);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        loginManager = new LoginManager();
    }
}
