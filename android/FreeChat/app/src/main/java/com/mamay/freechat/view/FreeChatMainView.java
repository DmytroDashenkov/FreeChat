package com.mamay.freechat.view;

import android.annotation.SuppressLint;
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
                        lengthBefore, start));
    }

    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    @SuppressLint("SetTextI18n")
    public void commitNewText(com.mamay.freechat.model.ChangedText changedText) {
        String text = getText().toString();
        String[] parts = {
                text.substring(changedText.getStartIndex(),
                        changedText.getStartIndex() + changedText.getLengthBeforeChange()),
                text.substring(changedText.getStartIndex() + changedText.getLengthBeforeChange() + 1)};
        setText(parts[0] + changedText.getText() + parts[1]);
    }

    /**
     * Interface for watching the text changing.
     * Helps to find out if the text was changed and how.
     */
    public interface TextChangedListener {

        /**
         * Called if the text was changed.
         *
         * @param changedText The ChangedText item, that represents the text change.
         */
        void onTextChanged(ChangedText changedText);

    }

    public class ChangedText {

        private String text;
        private int lengthBefore;
        private int startIndex;

        public ChangedText(String text, int lengthBefore, int startIndex) {
            this.text = text;
            this.lengthBefore = lengthBefore;
            this.startIndex = startIndex;
        }

        public String getText() {
            return text;
        }

        public int getLengthBefore() {
            return lengthBefore;
        }

        public int getStartIndex() {
            return startIndex;
        }
    }
}
