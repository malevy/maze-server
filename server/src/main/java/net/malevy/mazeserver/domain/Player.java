package net.malevy.mazeserver.domain;

import java.util.UUID;

public class Player {
    private String id;

    public Player() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
