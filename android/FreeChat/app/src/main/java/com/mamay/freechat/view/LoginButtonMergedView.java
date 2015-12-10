package com.mamay.freechat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mamay.freechat.R;

public class LoginButtonMergedView extends RelativeLayout {

    private TextView title;
    private ImageView icon;

    private Context context;

    public LoginButtonMergedView(Context context) {
        super(context);
        init(context, null);
    }

    public LoginButtonMergedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoginButtonMergedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoginButtonMergedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        this.context = context;
        mergeViews(context);
        assignContent(set);
    }

    private void mergeViews(Context context) {
        LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(ALIGN_PARENT_LEFT);

        title = new TextView(context);
        title.setId(View.generateViewId());
        addView(title, textParams);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RIGHT_OF, title.getId());

        icon = new ImageView(context);
        addView(icon, imageParams);
    }

    private void assignContent(AttributeSet attrs) {

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
                    0xCCC);
        } finally {
            customAttrs.recycle();
        }

        setContent(title, iconLink, bgColor);
    }

    private void setContent(String titleText, int iconLink, int bg) {
        title.setText(titleText);
        icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), iconLink));
        setBackgroundColor(bg);
    }
}
