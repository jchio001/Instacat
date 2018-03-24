package com.jonathan.catfeed.feed;

import android.widget.AbsListView;

import com.jonathan.catfeed.commons.GridCell.ItemType;
import com.jonathan.catfeed.data.FeedManager;

public class PagingScrollListener implements AbsListView.OnScrollListener {

    private boolean isLoading = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {
        int lastVisibleItemIndex = firstVisibleItem + visibleItemCount - 1;
        if (shouldRefresh(view, lastVisibleItemIndex, totalItemCount)) {
            if (!isLoading) {
                isLoading = true;
                FeedManager.get().requestImages();
            }
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    private boolean shouldRefresh(AbsListView view, int lastVisibleItemIndex, int totalItemCount) {
        return reachedEndOfPage(lastVisibleItemIndex, totalItemCount)
            && isRefreshIconCell(view, lastVisibleItemIndex);
    }

    private boolean reachedEndOfPage(int lastVisibleItemIndex, int totalItemCount) {
        return lastVisibleItemIndex >= totalItemCount - 1 && totalItemCount != 0;
    }

    private boolean isRefreshIconCell(AbsListView view, int index) {
        return view.getAdapter().getItemViewType(index) == ItemType.PROGRESS_BAR_CELL;
    }
}
