package com.grillo78.beychunks.capabilities;

import java.util.UUID;

public class Claimed implements IClaimed {

    private boolean claimed;
    private UUID uuid;

    @Override
    public boolean isClaimed() {
        return claimed;
    }

    @Override
    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
