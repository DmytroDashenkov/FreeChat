package com.mamay.freechat.manager;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.mamay.freechat.App;
import com.mamay.freechat.Const;
import com.mamay.freechat.auth.FacebookLoginer;
import com.mamay.freechat.auth.GoogleLoginer;
import com.mamay.freechat.auth.Loginer;
import com.mamay.freechat.auth.UsernameHolder;

/**
 * Log in manager, that helps the app to get and store user's identity.
 */
public class LoginManager implements UsernameHolder {

    /**
     * User name taken from a social network.
     */
    private String username = Const.login.DEFAULT_USERNAME;
    /**
     * Facebook SDK log in helper.
     */
    private Loginer facebook;
    /**
     * Google API helper.
     */
    private Loginer google;
    /**
     * Container for storing
     */
    private LogInState logInState;

    /**
     * Default class constructor.
     */
    public LoginManager() {
        logInState = new LogInState();
    }

    /**
     * Default activity setter.
     *
     * @param activity Login activity reference for getting it's result.
     */
    public void setActivity(Activity activity) {
        google = new GoogleLoginer(activity, this);
        facebook = new FacebookLoginer(activity, this);
    }

    /**
     * Getter method for user name.
     *
     * @return User name.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        if (username == null || username.length() == 0) {
            username = Const.login.DEFAULT_USERNAME;
        }
        this.username = username;
        App.getSharedPreferencesManager().write(Const.sharedprefs.USERNAME, username);
    }

    /**
     * Loges user in via facebook with permission 'public_profile'.
     * Allows to access user's page basic info.
     */
    public void loginViaFB() {
        facebook.login();

        logInState.facebook = isLoggedInViaFB();

        if (logInState.facebook) {
            ((FacebookLoginer) facebook).getFBName();
        }
    }

    /**
     * Activity result to be handled by google.
     */
    public void onGoogleActivityReturnsResult(Intent data) {
        google.onActivityResult(0, 0, data);
    }

    /**
     * Activity result to be handled by facebook.
     */
    public void onFacebookActivityReturnsResult(int requestCode, int resultCode, Intent data) {
        facebook.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Sign in the user via Google.
     */
    public void loginViaGoogle() {
        google.login();
        logInState.google = google.isLoggedIn();
    }

    /**
     * Determines if user is logged in with facebook.
     *
     * @return Is there an access token for facebook.
     */
    private boolean isLoggedInViaFB() {
        return facebook.isLoggedIn();
    }

    /**
     * Determines if user is logged in with Google.
     *
     * @return Is user signed in with Google.
     */
    private boolean isLoggedInViaGoogle() {
        return google.isLoggedIn();
    }

    /**
     * Is user logged in?
     *
     * @return <code>true</code> if user is logged in via one of social networks.
     */
    public boolean isLoggedIn() {
        return logInState.isLoggedIn();
    }

    /**
     * Log out from all accounts.
     */
    public void logout() {
        if (logInState.google) {
            google.logout();
        }
        if (logInState.facebook) {
            facebook.logout();
        }

        Log.w("login state", logInState.toString());
    }

    /**
     * Container class that holds the log in state for each of the social networks.
     */
    private class LogInState {

        /**
         * Is user logged in with facebook.
         */
        boolean facebook;
        /**
         * Is user logged in with google.
         */
        boolean google;

        /**
         * Is user logged in at all.
         *
         * @return <code>true</code> if user is logged in via one of social networks.
         */
        boolean isLoggedIn() {
            return facebook && google;
        }

        @Override
        public String toString() {
            return "LogInState{" +
                    "facebook=" + facebook +
                    ", google=" + google +
                    '}';
        }
    }
}
