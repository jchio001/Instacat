package com.jonathan.catfeed.feed;

import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jonathan.catfeed.api.ApiClient;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.commons.GridCell;
import com.jonathan.catfeed.commons.GridCell.ItemType;
import com.jonathan.catfeed.commons.GridCellFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class FeedAdapter extends BaseAdapter {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FeedState.INITIALIZING, FeedState.INITIALIZED})
    public @interface FeedState {
        int INITIALIZING = 0;
        int INITIALIZED = 1;
    }

    private List<GridCell> gridCells = new ArrayList<>();
    private @FeedState int feedState = FeedState.INITIALIZING;

    public FeedAdapter() {
        this.gridCells = GridCellFactory.getRenderingPage();
    }

    @Override
    public int getCount() {
        return gridCells.size();
    }

    @Override
    public boolean isEnabled(int position) {
        @ItemType int itemType = gridCells.get(position).getItemType();
        return itemType == ItemType.IMAGE_CELL || itemType == ItemType.REFRESH_ICON_CELL;
    }

    @Override
    public GridCell getItem(int position) {
        return gridCells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public @ItemType int getItemViewType(int position) {
        return gridCells.get(position).getItemType();
    }

    @Override
    public int getViewTypeCount() {
        return GridCell.ITEM_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return FeedViewManager.getView(gridCells.get(position), convertView, parent);
    }

    public void addImages(List<Image> images) {
        if (feedState == FeedState.INITIALIZING) {
            gridCells.clear();
            feedState = FeedState.INITIALIZED;
        } else {
            int currentSize = gridCells.size();
            if (currentSize > 0) {
                gridCells.remove(currentSize - 1);
                gridCells.remove(currentSize - 2);
            }
        }

        gridCells.addAll(images);
        gridCells.add(GridCellFactory.getEmptyCell());
        gridCells.add(GridCellFactory.getProgressBarCell());
        notifyDataSetChanged();
    }

    public void handleFailedRequest() {
        if (feedState == FeedState.INITIALIZING) {
            if (gridCells.size() == ApiClient.PAGE_SIZE) {
                gridCells.add(GridCellFactory.getEmptyCell());
                gridCells.add(GridCellFactory.getRefreshIconCell());
            }
        } else {
            gridCells.remove(gridCells.size() - 1);
            gridCells.add(GridCellFactory.getRefreshIconCell());
        }
        notifyDataSetChanged();
    }

    public void showProgessBarCell() {
        gridCells.remove(gridCells.size() - 1);
        gridCells.add(GridCellFactory.getRefreshIconCell());
        notifyDataSetChanged();
    }
}
