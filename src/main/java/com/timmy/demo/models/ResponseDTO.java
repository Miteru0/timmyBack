package com.timmy.demo.models;

public class ResponseDTO {

    private String emotion;
    private String text;

    public ResponseDTO() {
    }

    public ResponseDTO(String emotion, String text) {
        this.emotion = emotion;
        this.text = text;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
