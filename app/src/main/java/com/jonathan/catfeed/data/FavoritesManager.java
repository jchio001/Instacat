package com.jonathan.catfeed.data;

import android.content.Context;

import java.util.Map;

/**
 * This is SUPER JANK. DO NOT DO THIS EVER ON ANY SORT OF CONSUMER APPLICATION. I will delete this
 * shit at some point and use SQLlite.
 */
public class FavoritesManager {

    private static FavoritesManager instance;

    private Map<String, Boolean> favorites;
    private UpdateEventManager updateEventManager = new UpdateEventManager();

    public static FavoritesManager get() {
        if (instance == null) {
            synchronized (FavoritesManager.class) {
                if (instance == null) {
                    instance = new FavoritesManager();
                }
            }
        }

        return instance;
    }

    public void init(Context context) {
        favorites = SPUtils.retrieveFavorites(context);
    }

    public Map<String, Boolean> getFavorites() {
        return favorites;
    }

    public boolean isFavorite(String url) {
        if (favorites.containsKey(url)) {
            return favorites.get(url);
        }

        return false;
    }

    public void persistFavoriteState(Context context,
                                     String imageUrl,
                                     boolean isFavorite) {
        boolean wasFavorite = isFavorite(imageUrl);
        if (wasFavorite != isFavorite) {
            updateEventManager.publishUpdate();
        }

        favorites.put(imageUrl, isFavorite);
        SPUtils.persistFavoriteState(context, imageUrl, isFavorite);
    }

    public boolean consumeUpdateIfPresent() {
        return updateEventManager.consumeUpdateIfPresent();
    }
}
