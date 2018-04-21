package com.jonathan.catfeed.api;

/**
 * Used to calculate the delay for request more cat pictures
 */
public class DelayManager {

    private static DelayManager instance;

    private final long cooldown = 1500;
    private long lastActionTime = 0;

    public static DelayManager get() {
        if (instance == null) {
            synchronized (DelayManager.class) {
                if (instance == null) {
                    instance = new DelayManager();
                }
            }
        }

        return instance;
    }

    public void updateLastActionTime() {
        this.lastActionTime = System.currentTimeMillis();
    }

    public long calculateDelay() {
        long elapsedTime = System.currentTimeMillis() - lastActionTime;
        return (elapsedTime >= cooldown) ? 0 : cooldown - elapsedTime;
    }
}
