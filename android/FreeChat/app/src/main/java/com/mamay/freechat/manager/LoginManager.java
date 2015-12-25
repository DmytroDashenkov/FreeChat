package com.mamay.freechat.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Log in manager, that helps the app to get and store user's identity.
 */
public class LoginManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, FacebookCallback {

    /**
     * User name taken from a social network.
     */
    private String username = "username";
    /**
     * Facebook SDK log in helper.
     */
    private com.facebook.login.LoginManager facebook;
    /**
     * Google API helper.
     */
    private GoogleApiClient google;
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
        facebook = com.facebook.login.LoginManager.getInstance();
        facebook.setLoginBehavior(LoginBehavior.WEB_ONLY);
        facebook.registerCallback(CallbackManager.Factory.create(), this);

        google = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApiIfAvailable(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
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
     */
    public void loginViaFB(Activity activity) {
        //TODO // FIXME: 25.12.2015 native log in + getting name
        facebook.logInWithReadPermissions(activity, Arrays.asList("public_profile"));

        logInState.facebook = isLoggedInViaFB();

        if (logInState.facebook) {
            getFBName();
        }
        Log.w("login FB", "trying to log in");
        Log.w("lon in state", logInState.toString());
    }

    /**
     * Sign in the user via Google+.
     */
    public void loginViaGoogle() {
        //TODO // FIXME: 25.12.2015 log in with google
        google.connect();

        logInState.gPlus = isLoggedInViaGoogle();

        if (logInState.gPlus) {
            getGoogleName();
        }
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
     * Determines if user is logged in with Google+.
     *
     * @return Is user signed in with G+.
     */
    private boolean isLoggedInViaGoogle() {
        return google.isConnected();
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
                        } finally {
                            Log.w("facebook name", "now username is " + username);
                        }
                    }
                }
        ).executeAsync();
    }

    /**
     * Requests the user name from Google.
     */
    private void getGoogleName() {
        username = Plus.API.getName();
    }

    /**
     * Is user logged in?
     *
     * @return <code>true</code> if user is logged in via one of social networks.
     */
    public boolean isLoggedIn() {
        return logInState.isLoggedIn();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String s = (connectionResult.getErrorMessage() == null) ?
                "Google Play Services are not installed" : connectionResult.getErrorMessage();
        Log.e("Google connection error", s);
        Log.e("Google connection error", connectionResult.toString());
    }

    @Override
    public void onSuccess(Object o) {
        Log.w("log in", "Facebook log in succeed");
        Log.w("fb", o.toString());
        getFBName();
    }

    @Override
    public void onCancel() {
        Log.w("log in", "Facebook log in canceled");
    }

    @Override
    public void onError(FacebookException error) {
        Log.e("log in failed", error.getMessage());
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
        boolean gPlus;

        /**
         * Is user logged in at all.
         *
         * @return <code>true</code> if user is logged in via one of social networks.
         */
        boolean isLoggedIn() {
            return facebook && gPlus;
        }

        @Override
        public String toString() {
            return "LogInState{" +
                    "facebook=" + facebook +
                    ", gPlus=" + gPlus +
                    '}';
        }
    }
}
