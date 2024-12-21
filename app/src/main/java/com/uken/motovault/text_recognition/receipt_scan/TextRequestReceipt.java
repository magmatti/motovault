package com.uken.motovault.text_recognition.receipt_scan;

public class TextRequestReceipt {
    private String text;

    public TextRequestReceipt(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
