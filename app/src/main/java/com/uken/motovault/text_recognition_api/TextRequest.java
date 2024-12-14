package com.uken.motovault.text_recognition_api;

public class TextRequest {
    private String text;

    // Konstruktor
    public TextRequest(String text) {
        this.text = text;
    }

    // Getter
    public String getText() {
        return text;
    }

    // Setter
    public void setText(String text) {
        this.text = text;
    }
}

