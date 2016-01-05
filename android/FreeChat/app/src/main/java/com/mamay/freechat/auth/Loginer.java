package com.mamay.freechat.auth;

import android.app.Activity;
import android.content.Intent;

public abstract class Loginer {

    /**
     * <code>Activity</code> for result.
     */
    protected Activity loginActivity;
    /**
     * Helper callback for username.
     */
    protected UsernameHolder usernameHolder;

    public Loginer(Activity loginActivity, UsernameHolder usernameHolder) {
        this.loginActivity = loginActivity;
        this.usernameHolder = usernameHolder;
    }

    public abstract void login();

    public abstract void logout();

    public abstract boolean isLoggedIn();

    public abstract void onActivityResult(int request, int response, Intent data);
}
