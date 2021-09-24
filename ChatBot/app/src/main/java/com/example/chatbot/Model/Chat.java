package com.example.chatbot.Model;

public class Chat {

    public static final int BOT_TYPE = 0;
    public static final int CLIENT_TYPE = 1;

    private String message;
    private int type;

    public Chat() {
    }

    public Chat(String message, int type) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
