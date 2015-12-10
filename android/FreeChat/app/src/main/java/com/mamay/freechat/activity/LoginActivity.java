package com.mamay.freechat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mamay.freechat.R;
import com.mamay.freechat.manager.LoginManager;

public class LoginActivity extends AppCompatActivity {

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
