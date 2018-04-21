package com.jonathan.catfeed.feed;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jonathan.catfeed.data.FavoritesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesAdapter extends BaseAdapter {

    private static final int DEFAULT_CAPACITY = 20;

    private final Map<String, Boolean> favoritesMap = FavoritesManager.get().getFavorites();

    private List<String> favoritesUrls = new ArrayList<>(DEFAULT_CAPACITY);

    public FavoritesAdapter() {
        retrieveFavoritesUrls(favoritesUrls);
    }

    @Override
    public int getCount() {
        return favoritesUrls.size();
    }

    @Override
    public String getItem(int position) {
        return favoritesUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return FeedViewManager.getImageCellView(favoritesUrls.get(position), convertView, parent);
    }

    public void retrieveFavoritesUrls(List<String> favoritesUrls) {
        for (Map.Entry<String, Boolean> favoriteEntry : favoritesMap.entrySet()) {
            if (favoriteEntry.getValue()) {
                favoritesUrls.add(favoriteEntry.getKey());
            }
        }
    }

    public void maybeUpdate() {
        if (FavoritesManager.get().consumeUpdateIfPresent()) {
            List<String> updatedFavoritesUrls = new ArrayList<>(DEFAULT_CAPACITY);
            retrieveFavoritesUrls(updatedFavoritesUrls);
            this.favoritesUrls = updatedFavoritesUrls;
            notifyDataSetChanged();
        }
    }
}
