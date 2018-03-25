package com.jonathan.catfeed.data;

import android.content.Context;
import android.preference.PreferenceManager;

public class SPUtils {

    public static boolean isFavorite(Context context, String imageId) {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(imageId, false);
    }

    public static void persistFavoriteState(Context context,
                                            String imageId,
                                            boolean isFavorite) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(imageId, isFavorite)
            .apply();
    }
}
