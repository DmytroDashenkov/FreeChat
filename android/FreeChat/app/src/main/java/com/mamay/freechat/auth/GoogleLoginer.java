package com.mamay.freechat.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mamay.freechat.Const;

/**
 * Helper class for google log in.
 */
public class GoogleLoginer extends Loginer implements GoogleApiClient.OnConnectionFailedListener {

    /**
     * Google API helper.
     */
    private GoogleApiClient google;

    public GoogleLoginer(Activity loginActivity, UsernameHolder usernameHolder) {
        super(loginActivity, usernameHolder);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestEmail()
                .requestProfile()
                .build();

        google = new GoogleApiClient.Builder(loginActivity.getApplicationContext())
                .enableAutoManage((FragmentActivity) loginActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void login() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
        loginActivity.startActivityForResult(intent, Const.login.SIGN_IN_WITH_GOOGLE);
    }

    @Override
    public void logout() {
        //TODO test me
        Auth.GoogleSignInApi.revokeAccess(google);
    }

    @Override
    public boolean isLoggedIn() {
        return google.isConnected();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        try {
            String s = result.getSignInAccount().getEmail();
            usernameHolder.setUsername(s);
            Log.w("google name", s);
        } catch (Throwable t) {
            Log.e("error", t.getMessage());
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String s = (connectionResult.getErrorMessage() == null) ?
                "Google Play Services are not installed" : connectionResult.getErrorMessage();
        Log.e("Google connection error", s);
        Log.e("Google connection error", connectionResult.toString());
    }
}
