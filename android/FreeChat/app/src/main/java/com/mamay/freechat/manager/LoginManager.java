package com.mamay.freechat.manager;

/**
 * Log in manager, that helps the app to get and store user's identity.
 */
public class LoginManager {

    private String username = "mamay";

    private com.facebook.login.LoginManager facebook;

    public String getUsername() {
        return username;
    }

    public boolean loginViaFB() {
        return false;
    }

    public boolean isLoggedIn() {
        return false;
    }
}
