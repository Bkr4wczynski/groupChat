package com.bartek.groupchat.utils;

import java.io.Serializable;

public class Packet implements Serializable {
    private Type type;
    private String content;

    public Packet(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}

