package com.jonathan.catfeed.data;

public class UpdateEventManager {

    private boolean hasUpdated = false;

    public void publishUpdate() {
        hasUpdated = true;
    }

    public boolean consumeUpdateIfPresent() {
        boolean updateConsumed = hasUpdated;
        hasUpdated = false;
        return updateConsumed;
    }
}
