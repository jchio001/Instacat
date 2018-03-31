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

    public boolean isFavorite(String url) {
        if (favorites.containsKey(url)) {
            return favorites.get(url);
        }

        return false;
    }

    public void persistFavoriteState(Context context,
                                     String imageId,
                                     boolean isFavorite) {
        favorites.put(imageId, isFavorite);
        SPUtils.persistFavoriteState(context, imageId, isFavorite);
    }
}
