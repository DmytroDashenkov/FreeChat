package com.mamay.freechat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Custom EditText that is used for this app.
 * The view knows if it's content was changed and where exactly.
 */
public class FreeChatMainView extends EditText {

    private TextChangedListener textChangedListener;

    public FreeChatMainView(Context context) {
        super(context);
        init();
    }

    public FreeChatMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FreeChatMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FreeChatMainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setSingleLine(false);
        setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        textChangedListener.onTextChanged(
                new ChangedText((String) text.subSequence(start, start + lengthAfter),
                        lengthBefore));
    }

    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public interface TextChangedListener {

        void onTextChanged(ChangedText changedText);

    }

    public class ChangedText {

        private String text;
        private int lengthBefore;

        public ChangedText(String text, int lengthBefore) {
            this.text = text;
            this.lengthBefore = lengthBefore;
        }
    }
}
