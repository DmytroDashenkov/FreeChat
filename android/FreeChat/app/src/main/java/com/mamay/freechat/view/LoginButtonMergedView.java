package com.mamay.freechat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mamay.freechat.R;

public class LoginButtonMergedView extends RelativeLayout {

    public LoginButtonMergedView(Context context) {
        super(context);
        init(context);
    }

    public LoginButtonMergedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoginButtonMergedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoginButtonMergedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mergeViews(context);
    }

    private void mergeViews(Context context) {
        LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(ALIGN_PARENT_LEFT);

        TextView title = new TextView(context);
        title.setId(View.generateViewId());
        addView(title, textParams);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RIGHT_OF, title.getId());

        ImageView icon = new ImageView(context);
        addView(icon, imageParams);
    }

    private void assignContent(Context context, AttributeSet attrs) {

        String title = "";
        int iconLink = 0;
        int bgColor = 0;

        TypedArray customAttrs = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LoginButtonMergedView, 0, 0);
        try {
            title = customAttrs.getString(R.styleable.LoginButtonMergedView_loginText);
            iconLink = customAttrs.getInt(R.styleable.LoginButtonMergedView_loginIcon,
                    R.mipmap.ic_launcher);
            bgColor = customAttrs.getColor(R.styleable.LoginButtonMergedView_loginBackgroundColor,
                    0xFFF);
        } finally {
            customAttrs.recycle();
        }
    }
}
