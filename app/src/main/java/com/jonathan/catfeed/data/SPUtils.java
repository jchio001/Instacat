package com.jonathan.catfeed.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.jonathan.catfeed.api.models.Image;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SPUtils {

    @SuppressWarnings("unchecked")
    public static Map<String, Boolean> retrieveFavorites(Context context) {
        return (Map<String, Boolean>) PreferenceManager.getDefaultSharedPreferences(context)
            .getAll();
    }

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
