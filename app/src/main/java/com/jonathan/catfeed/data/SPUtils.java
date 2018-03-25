package com.jonathan.catfeed.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.jonathan.catfeed.api.models.Image;

import java.util.List;

public class SPUtils {

    public static String[] getFavoritesIds(Context context) {
        String serializedIds = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(IntentKeys.FAVORITED_IMAGE_IDS, null);

        return serializedIds != null ? serializedIds.split(",") : new String[]{};
    }

    public static void persistFavoritesId(Context context, List<String> imageIds) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(IntentKeys.FAVORITED_IMAGE_IDS, TextUtils.join(",", imageIds))
            .apply();
    }

    public static Image getImage(Context context, String imageId) {
        SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context);
        return new Image()
            .setId(imageId)
            .setUrl(sharedPreferences
                .getString(imageId + "_" + IntentKeys.IMAGE_URL, null));
    }

    public static void persistImageUrl(Context context, String imageId, String imageUrl) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(imageId + "_" + IntentKeys.IMAGE_URL, imageUrl)
            .apply();
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
