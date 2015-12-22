package com.mamay.freechat;

import android.app.Application;
import android.graphics.Typeface;

import com.mamay.freechat.manager.LoginManager;
import com.mamay.freechat.manager.NetworkManager;

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

    public static LoginManager getLoginManager() {
        return loginManager;
    }

    public static Typeface getChatFont() {
        return chatFont;
    }

    public static NetworkManager getNetworkManager() {
        return networkManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManager(getApplicationContext());
        chatFont = Typeface.createFromAsset(getAssets(), "chatfont.ttf");
        networkManager = new NetworkManager(this);
    }
}
