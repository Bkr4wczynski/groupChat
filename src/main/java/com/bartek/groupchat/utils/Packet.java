package com.bartek.groupchat.utils;

import java.io.Serializable;

public class Packet implements Serializable {
    private PacketType packetType;
    private String content;

    public Packet(PacketType packetType, String content) {
        this.packetType = packetType;
        this.content = content;
    }

    public PacketType getType() {
        return packetType;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}

