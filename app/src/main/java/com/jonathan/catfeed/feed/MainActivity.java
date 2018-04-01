package com.jonathan.catfeed.feed;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.commons.GridCell;
import com.jonathan.catfeed.commons.GridCell.ItemType;
import com.jonathan.catfeed.data.FavoritesManager;
import com.jonathan.catfeed.data.FeedManager;
import com.jonathan.catfeed.data.FeedManager.FeedListener;
import com.jonathan.catfeed.ui.BottomNavContentPresenter;
import com.jonathan.catfeed.ui.PaneGridView;
import com.jonathan.catfeed.ui.PaneImageLayout;
import com.jonathan.catfeed.ui.SinglePaneFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.parent) RelativeLayout parent;
    @BindView(R.id.container) SinglePaneFrameLayout container;
    @BindView(R.id.cat_grid_view) GridView catGridView;
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNav;

    private FeedAdapter feedAdapter = new FeedAdapter();
    private PagingScrollListener pagingScrollListener = new PagingScrollListener();

    private BottomNavContentPresenter bottomNavContentManager;

    private OnNavigationItemSelectedListener bottomNavListener = item -> {
        if (item.getItemId() == R.id.action_home) {
            bottomNavContentManager.focusTab(R.id.action_home);
            return true;
        } else {
            if (!bottomNavContentManager.contains(item.getItemId())) {
                SinglePaneFrameLayout favoritesContainer = (SinglePaneFrameLayout)
                    LayoutInflater.from(this).inflate(R.layout.container_grid_layout, parent, false);
                bottomNavContentManager.addContainer(item.getItemId(), favoritesContainer);
            }

            bottomNavContentManager.focusTab(item.getItemId());

            return true;
        }
    };

    private FeedListener feedListener = new FeedListener() {
        @Override
        public void onSuccess(List<Image> images) {
            DelayManager.get().updateLastActionTime();
            feedAdapter.addImages(images);
            pagingScrollListener.setLoading(false);
        }

        @Override
        public void onFailure(Throwable t) {
            feedAdapter.handleFailedRequest();
            Toast.makeText(MainActivity.this, R.string.failed_request, Toast.LENGTH_SHORT).show();
            pagingScrollListener.setLoading(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavContentManager = new BottomNavContentPresenter(parent);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);

        int firstTabId = bottomNav.getMenu().getItem(0).getItemId();
        bottomNavContentManager
            .addInitialContainer(firstTabId, container);
        bottomNavContentManager.focusTab(firstTabId);

        FavoritesManager.get().init(this);

        catGridView.setAdapter(feedAdapter);
        catGridView.setOnScrollListener(pagingScrollListener);

        FeedManager.get().listen(feedListener);
        FeedManager.get().requestImages();
    }

    @Override
    protected void onDestroy() {
        FeedManager.get().destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        int newTabId = bottomNavContentManager.removeCurrentPane();
        if (newTabId != BottomNavContentPresenter.NO_PREVIOUS_TAB_ID) {
            bottomNav.setSelectedItemId(newTabId);
        } else {
            super.onBackPressed();
        }
    }

    @OnItemClick(R.id.cat_grid_view)
    public void onGridCellClick(int position) {
        GridCell gridCell = feedAdapter.getItem(position);
        switch (gridCell.getItemType()) {
            case ItemType.REFRESH_ICON_CELL:
                pagingScrollListener.setLoading(true);
                feedAdapter.showProgessBarCell();
                FeedManager.get().requestImages();
                break;
            case ItemType.IMAGE_CELL:
                Image image = (Image) gridCell;
                if (image.getUrl() != null) {
                    bottomNavContentManager.overlay(
                        new PaneImageLayout(this, null, image.getUrl()));
                }
        }
    }
}
