package com.mamay.freechat.model;

import com.mamay.freechat.App;
import com.mamay.freechat.view.FreeChatMainView;

import java.util.Date;

public class ChangedText {

    private String text;
    private int startIndex;
    private int lengthBeforeChange;
    private String author;
    private Date date;

    public static ChangedText fromViewText(FreeChatMainView.ChangedText changedText) {
        ChangedText ct = new ChangedText();

        ct.setText(changedText.getText())
                .setStartIndex(changedText.getStartIndex())
                .setLengthBeforeChange(changedText.getLengthBefore())
                .setDate(new Date())
                .setAuthor(App.getLoginManager().getUsername());

        return ct;
    }

    public String getText() {
        return text;
    }

    public ChangedText setText(String text) {
        this.text = text;
        return this;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public ChangedText setStartIndex(int startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public int getLengthBeforeChange() {
        return lengthBeforeChange;
    }

    public ChangedText setLengthBeforeChange(int lengthBeforeChange) {
        this.lengthBeforeChange = lengthBeforeChange;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ChangedText setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ChangedText setDate(Date date) {
        this.date = date;
        return this;
    }
}
