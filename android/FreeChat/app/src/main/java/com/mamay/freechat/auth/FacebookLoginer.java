package com.mamay.freechat.auth;

import android.app.Activity;
import android.content.Intent;
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
import com.mamay.freechat.Const;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Helper class for facebook log in.
 */
public class FacebookLoginer extends Loginer {

    /**
     * Facebook SDK log in helper.
     */
    private com.facebook.login.LoginManager facebook;
    /**
     * Facebook callback manager, the connector for Facebook SDK and application.
     */
    private CallbackManager fbCallback;

    public FacebookLoginer(Activity activity, UsernameHolder usernameHolder) {
        super(activity, usernameHolder);

        this.loginActivity = activity;

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
    }

    @Override
    public void login() {
        facebook.logInWithReadPermissions(loginActivity, Arrays.asList(Const.facebook.PUBLIC_PROFILE));
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean isLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        fbCallback.onActivityResult(request, response, data);
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
                            }
                        }
                    }
                }
        ).executeAsync();
    }

    /**
     * Requests the user name from facebook.
     */
    public void getFBName() {
        executeFBRequest(
                AccessToken.getCurrentAccessToken().getUserId(),
                Const.facebook.NAME,
                new FacebookOnRequestListener() {
                    @Override
                    public void onRequest(Object result) {
                        usernameHolder.setUsername((String) result);
                    }
                });
    }

    private interface FacebookOnRequestListener {
        void onRequest(Object result);
    }
}