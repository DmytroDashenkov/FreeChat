package com.mamay.freechat.presenter;

import android.view.View;

/**
 * Used to manage the connection between view and model.
 */
public abstract class AbstractPresenter {

    public abstract void onCreate(View view);

    public abstract void onDestroy();
}
