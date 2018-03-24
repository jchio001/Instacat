package com.jonathan.catfeed.feed;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.commons.GridCell;
import com.jonathan.catfeed.commons.GridCell.ItemType;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class FeedViewManager {

    private static final String INVALID_ITEM_TYPE_TEMPLATE = "Invalid item type %d";

    public static View getView(GridCell gridCell, View convertView, ViewGroup parent) {
        switch (gridCell.getItemType()) {
            case ItemType.IMAGE_CELL:
                return FeedViewManager.getImageCellView((Image) gridCell, convertView, parent);
            case ItemType.EMPTY_SPACE_CELL:
                return FeedViewManager.getEmptySquareView(convertView, parent);
            case ItemType.PROGRESS_BAR_CELL:
                return FeedViewManager.getProgressBarCellView(convertView, parent);
            case ItemType.REFRESH_ICON_CELL:
                return FeedViewManager.getRefreshIconCellView(convertView, parent);
            default:
                throw new IllegalStateException(String.format(Locale.US,
                    INVALID_ITEM_TYPE_TEMPLATE, gridCell.getItemType()));
        }
    }

    private static View getImageCellView(Image image, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_cell, parent, false);
        }

        SquareImageView squareImageView = (SquareImageView) convertView;
        squareImageView.setImageDrawable(null);

        if (image.getUrl() != null) {
            Picasso.get()
                .load(image.getUrl())
                .into(squareImageView);
        }

        return convertView;
    }

    private static View getEmptySquareView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empty_cell, parent, false);
        }

        return convertView;
    }

    private static View getProgressBarCellView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_bar_cell, parent, false);
        }

        return convertView;
    }

    private static View getRefreshIconCellView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.refresh_icon_cell, parent, false);
        }

        return convertView;
    }
}
