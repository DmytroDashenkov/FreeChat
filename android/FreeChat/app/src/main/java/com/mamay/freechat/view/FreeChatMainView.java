package com.mamay.freechat.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Custom EditText that is used for this app.
 * The view knows if it's content was changed and where exactly.
 */
public class FreeChatMainView extends EditText {

    /**
     * Defines if the change is sourced from the view (false) or outside (true).
     */
    private boolean shouldHandleChange = true;
    /**
     * Listener for the text change event.
     */
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

    /**
     * Initializes the view.
     */
    private void init() {
        setSingleLine(false);
        setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        setGravity(Gravity.TOP | Gravity.START);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (shouldHandleChange && textChangedListener != null) {
            textChangedListener.onTextChanged(
                    new ChangedText(
                            text.subSequence(start, start + lengthAfter).toString(),
                            lengthBefore,
                            start));
        } else {
            shouldHandleChange = true;
        }
    }

    /**
     * Default setter for the text change listener.
     *
     * @param textChangedListener TextChangedListener instance to be used.
     */
    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    /**
     * Writes the changed text onto the view.
     *
     * @param changedText The content change.
     */
    @SuppressLint("SetTextI18n")
    public void commitNewText(com.mamay.freechat.model.ChangedText changedText) {
        String text = getText().toString();
        String[] parts = {
                text.substring(changedText.getStartIndex(),
                        changedText.getStartIndex() + changedText.getLengthBeforeChange()),
                text.substring(changedText.getStartIndex() + changedText.getLengthBeforeChange() + 1)};
        shouldHandleChange = false;
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

    /**
     * Class that represents the view content change, made by user.
     */
    public class ChangedText {

        /**
         * Inserted text.
         */
        private String text;
        /**
         * Length of the deleted text.
         */
        private int lengthBefore;
        /**
         * Index of the change start.
         */
        private int startIndex;

        /**
         * Default constructor.
         *
         * @param text         Inserted text.
         * @param lengthBefore Length of the deleted text.
         * @param startIndex   Index of the change start.
         */
        public ChangedText(String text, int lengthBefore, int startIndex) {
            this.text = text;
            this.lengthBefore = lengthBefore;
            this.startIndex = startIndex;
        }

        /**
         * Default getter for the inserted text.
         *
         * @return Inserted text.
         */
        public String getText() {
            return text;
        }

        /**
         * Default getter for the deleted text length.
         *
         * @return Deleted text length.
         */
        public int getLengthBefore() {
            return lengthBefore;
        }

        /**
         * Default getter for the start index.
         *
         * @return Start index.
         */
        public int getStartIndex() {
            return startIndex;
        }
    }
}
