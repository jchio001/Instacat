package com.jonathan.catfeed.data;


import android.content.Context;

import com.jonathan.catfeed.api.models.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesManager {

    private Set<Image> favoritedImages = new HashSet<>();

    private static FavoritesManager instance;

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

    public FavoritesManager populate(Context context) {
        String[] imageIds = SPUtils.getFavoritesIds(context);

        for (String imageId : imageIds) {
          favoritedImages.add(SPUtils.getImage(context, imageId));
        }

        return this;
    }

    public boolean isFavorite(Image image) {
        return favoritedImages.contains(image);
    }

    public void addFavorite(Image image) {
        favoritedImages.add(image);
    }

    public void unfavorite(Image image) {
        favoritedImages.remove(image);
    }

    public void persist(Context context) {
        List<String> imageIds =  new ArrayList<>(favoritedImages.size());

        for (Image image : favoritedImages) {
            imageIds.add(image.getId());
            SPUtils.persistImageUrl(context, image.getId(), image.getUrl());
        }

        SPUtils.persistFavoritesId(context, imageIds);
    }
}
