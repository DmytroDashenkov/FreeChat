package com.mamay.freechat.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mamay.freechat.R;

/**
 * Used to perform a button for the log in action.
 * Contains of a <code>TextView</code> and an <code>ImageView</code>.
 * Has custom attributes:
 * - <code>loginText</code>: title of the button;
 * - <code>loginIcon</code>: icon on the button;
 * - <code>loginBackgroundColor</code>: button's background color;
 * - <code>loginBackgroundColor</code>: label text color (white by default).
 * The view is useful only if all the attributes above are defined in XML.
 */
public class LoginButtonMergedView extends RelativeLayout {

    /**
     * The view that displays the button's title.
     */
    private TextView title;
    /**
     * The view that displays the button's icon.
     */
    private ImageView icon;
    /**
     * Application context.
     */
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

    /**
     * Initializes the view.
     *
     * @param context Application context.
     * @param set     Set of the view's custom attributes.
     */
    private void init(Context context, AttributeSet set) {
        this.context = context;
        mergeViews(context);
        assignContent(set);
    }

    /**
     * Initializes the view's components and set them to the layout.
     *
     * @param context Application context.
     */
    private void mergeViews(Context context) {
        LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(ALIGN_PARENT_LEFT);
        textParams.addRule(CENTER_VERTICAL);
        textParams.setMarginStart(context.getResources()
                .getDimensionPixelOffset(R.dimen.login_button_merged_view_elements_margin));
        textParams.setMarginEnd(context.getResources()
                .getDimensionPixelOffset(R.dimen.login_button_merged_view_elements_margin));

        title = new TextView(context);
        //title.setId(View.generateViewId());
        addView(title, textParams);

        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //imageParams.addRule(RIGHT_OF, title.getId());
        imageParams.addRule(ALIGN_PARENT_END);
        imageParams.addRule(ALIGN_PARENT_RIGHT);
        imageParams.setMarginStart(context.getResources()
                .getDimensionPixelOffset(R.dimen.login_button_merged_view_elements_margin));
        imageParams.setMarginEnd(context.getResources()
                .getDimensionPixelOffset(R.dimen.login_button_merged_view_elements_margin));

        icon = new ImageView(context);
        icon.setMaxWidth(context.getResources()
                .getDimensionPixelSize(R.dimen.login_button_merged_view_icon_size));
        addView(icon, imageParams);
    }

    /**
     * Assigns custom attributes to the view.
     *
     * @param attrs Attributes to assign.
     */
    private void assignContent(AttributeSet attrs) {

        String title = "";
        Drawable iconLink = null;
        int bgColor = 0;

        TypedArray customAttrs = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LoginButtonMergedView, 0, 0);
        try {
            title = customAttrs.getString(R.styleable.LoginButtonMergedView_loginText);
            iconLink = customAttrs.getDrawable(R.styleable.LoginButtonMergedView_loginIcon);
            bgColor = customAttrs.getColor(R.styleable.LoginButtonMergedView_loginBackgroundColor,
                    0xCCC);
            //Set color to label.
            this.title.setTextColor(customAttrs.getColor(
                    R.styleable.LoginButtonMergedView_loginTextColor,
                    Color.WHITE));
        } finally {
            customAttrs.recycle();
        }

        setContent(title, iconLink, bgColor);
    }

    /**
     * Applies values of the custom attributes to the view.
     *
     * @param titleText Title of the view.
     * @param iconLink  Icon of the log in way.
     * @param bg        View's background.
     */
    private void setContent(String titleText, Drawable iconLink, int bg) {
        title.setText(titleText);
        icon.setImageDrawable(iconLink);
        setBackgroundColor(bg);
    }
}
