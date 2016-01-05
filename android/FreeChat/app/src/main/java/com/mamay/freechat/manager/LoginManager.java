package com.mamay.freechat.manager;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mamay.freechat.App;
import com.mamay.freechat.Const;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Log in manager, that helps the app to get and store user's identity.
 */
public class LoginManager implements GoogleApiClient.OnConnectionFailedListener {

    /**
     * User name taken from a social network.
     */
    private String username = Const.login.DEFAULT_USERNAME;
    /**
     * Facebook SDK log in helper.
     */
    private com.facebook.login.LoginManager facebook;
    /**
     * Facebook callback manager, the connector for Facebook SDK and application.
     */
    private CallbackManager fbCallback;
    /**
     * Google API helper.
     */
    private GoogleApiClient google;
    /**
     * Container for storing
     */
    private LogInState logInState;
    /**
     * <code>Activity</code> for result.
     */
    private Activity loginActivity;

    /**
     * Default class constructor.
     *
     * @param activity Login activity reference for getting it's result.
     */
    public LoginManager(Activity activity) {
        logInState = new LogInState();

        loginActivity = activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        facebook = com.facebook.login.LoginManager.getInstance();
        fbCallback = CallbackManager.Factory.create();
        facebook.registerCallback(fbCallback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w("log in", "Facebook log in succeed");
                Log.w("fb login result", loginResult.toString());
            }

            @Override
            public void onCancel() {
                Log.w("log in", "Facebook log in canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("log in failed", error.getMessage());
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestEmail()
                .build();

        google = new GoogleApiClient.Builder(activity.getApplicationContext())
                .enableAutoManage((FragmentActivity) loginActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
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
     * Username setter for private access.
     *
     * @param username User name value.
     */
    private void setUsername(String username) {
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
        facebook.logInWithReadPermissions(loginActivity, Arrays.asList(Const.facebook.PUBLIC_PROFILE));

        logInState.facebook = isLoggedInViaFB();

        if (logInState.facebook) {
            getFBName();
        }
    }

    public void onFacebookActivityReturnsResult(int requestCode, int resultCode, Intent data) {
        fbCallback.onActivityResult(requestCode, resultCode, data);
    }

    public void onGoogleActivityReturnsResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        try {
            String s = result.getSignInAccount().getDisplayName();
            setUsername(s);
            Log.w("google name", s);
        } catch (Throwable t) {
            Log.e("error", t.getMessage());
        }

    }

    /**
     * Sign in the user via Google.
     */
    public void loginViaGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
        loginActivity.startActivityForResult(intent, Const.login.SIGN_IN_WITH_GOOGLE);
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
     * Determines if user is logged in with Google.
     *
     * @return Is user signed in with Google.
     */
    private boolean isLoggedInViaGoogle() {
        return google.isConnected();
    }

    /**
     * Requests the user name from facebook.
     */
    private void getFBName() {
        executeFBRequest(
                AccessToken.getCurrentAccessToken().getUserId(),
                Const.facebook.NAME,
                new FacebookOnRequestListener() {
                    @Override
                    public void onRequest(Object result) {
                        setUsername((String) result);
                    }
                });
    }

    /**
     * Requests data from facebook server.
     *
     * @param url      Request URL.
     * @param request  Value to search for.
     * @param listener Callback for data saving.
     */
    private void executeFBRequest(String url, final String request, final FacebookOnRequestListener listener) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + url,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() != null) {
                            Log.e("Facebook API call error",
                                    response.getError().getErrorType()
                                            + response.getError().getErrorMessage());
                        } else {
                            try {
                                if (response.getJSONObject() != null) {
                                    listener.onRequest(response.getJSONObject().getString(request));
                                }
                            } catch (JSONException e) {
                                Log.wtf("JSONException", e.getMessage());
                            } finally {
                                Log.w("facebook name", "now username is " + username);
                            }
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String s = (connectionResult.getErrorMessage() == null) ?
                "Google Play Services are not installed" : connectionResult.getErrorMessage();
        Log.e("Google connection error", s);
        Log.e("Google connection error", connectionResult.toString());
    }

    private interface FacebookOnRequestListener {
        void onRequest(Object result);
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
