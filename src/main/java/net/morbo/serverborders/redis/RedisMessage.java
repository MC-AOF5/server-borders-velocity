package net.morbo.serverborders.redis;

import net.morbo.serverborders.WorldDirection;

import java.util.UUID;

public class RedisMessage {
    public static String REDIS_CHANNEL = "ServerBorders";

    private final String playerUUID;
    private final WorldDirection collidedDirection;

    public RedisMessage(String playerUUID, WorldDirection collidedDirection) {
        this.playerUUID = playerUUID;
        this.collidedDirection = collidedDirection;
    }

    public RedisMessage(String rawMessage) {
        String[] msgElements = rawMessage.split("|");
        this.playerUUID = msgElements[1];
        this.collidedDirection = WorldDirection.valueOf(msgElements[2]);
    }

    public String getRawMessage() {
        return this.playerUUID + "|" + this.collidedDirection.toString();
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public WorldDirection getCollidedDirection() {
        return collidedDirection;
    }
}
