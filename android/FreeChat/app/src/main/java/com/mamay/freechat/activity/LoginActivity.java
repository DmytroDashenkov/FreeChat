package com.mamay.freechat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mamay.freechat.App;
import com.mamay.freechat.R;
import com.mamay.freechat.manager.LoginManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginManager = App.getLoginManager();

        findViewById(R.id.login_via_fb).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        loginManager.loginViaFB(this);

    }
}
