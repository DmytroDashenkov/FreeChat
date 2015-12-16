package com.mamay.freechat.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Log in manager, that helps the app to get and store user's identity.
 */
public class LoginManager {

    /**
     * User name taken from a social network.
     */
    private String username = "username";
    /**
     * Facebook SDK log in helper.
     */
    private com.facebook.login.LoginManager facebook;
    /**
     * Container for storing
     */
    private LogInState logInState;

    /**
     * Default class constructor.
     *
     * @param context Application context.
     */
    public LoginManager(Context context) {
        logInState = new LogInState();
        FacebookSdk.sdkInitialize(context);
    }

    /**
     * Getter method for user name.
     *
     * @return User name.
     */
    public String getUsername() {
        return username;
    }


    /**
     * Loges user in via facebook with permission 'public_profile'.
     * Allows to access user's page basic info.
     *
     * @param activity Activity to be fed to facebook API.
     * @return Was the log in successful.
     */
    public boolean loginViaFB(Activity activity) {
        facebook = com.facebook.login.LoginManager.getInstance();
        facebook.logInWithReadPermissions(activity, Arrays.asList("public_profile"));

        if (isLoggedInViaFB()) {
            logInState.facebook = true;
            getFBName();
            return true;
        }

        return false;
    }

    /**
     * Determines if user is logged in with facebook.
     *
     * @return Is there an access token for facebook.
     */
    private boolean isLoggedInViaFB() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * Requests the user name from facebook.
     */
    private void getFBName() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{user-name}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() != null) {
                            Log.e("Facebook API call error", response.getError().getErrorMessage());
                        }
                        try {
                            username = response.getJSONObject().getString("user-name");
                        } catch (JSONException e) {
                            Log.wtf("JSONException", e.getMessage());
                        }
                    }
                }
        ).executeAsync();
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
     * Container class that holds the log in state for each of the social networks.
     */
    private class LogInState {

        /**
         * Is user logged in with facebook.
         */
        boolean facebook;

        /**
         * Is user logged in at all.
         *
         * @return <code>true</code> if user is logged in via one of social networks.
         */
        boolean isLoggedIn() {
            return facebook;
        }
    }
}
