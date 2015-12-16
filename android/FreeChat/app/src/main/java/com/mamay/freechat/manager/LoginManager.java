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

    private String username = "username";

    private com.facebook.login.LoginManager facebook;
    private LogInState logInState;


    public LoginManager(Context context) {
        logInState = new LogInState();
        FacebookSdk.sdkInitialize(context);
    }

    public String getUsername() {
        return username;
    }


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

    private boolean isLoggedInViaFB() {
        return AccessToken.getCurrentAccessToken() != null;
    }

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

    public boolean isLoggedIn() {
        return isLoggedInViaFB();
    }

    private class LogInState {

        boolean facebook;
    }
}
