package com.jonathan.catfeed.feed;

/**
 * Used to calculate the delay for request more cat pictures
 */
public class DelayManager {

    private static DelayManager instance;

    private final long cooldown = 2500;
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

        if (elapsedTime >= cooldown) {
            return 0;
        } else {
            return cooldown - elapsedTime;
        }
    }
}
