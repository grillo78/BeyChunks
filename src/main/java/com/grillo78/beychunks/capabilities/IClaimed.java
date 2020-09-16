package com.grillo78.beychunks.capabilities;

import java.util.UUID;

public interface IClaimed {

    boolean isClaimed();

    void setClaimed(boolean claimed);

    UUID getUUID();

    void setUUID(UUID uuid);

}
