package com.jonathan.catfeed.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.commons.GridCell;
import com.jonathan.catfeed.commons.GridCell.ItemType;
import com.jonathan.catfeed.data.FeedManager;
import com.jonathan.catfeed.data.FeedManager.FeedListener;
import com.jonathan.catfeed.data.IntentKeys;
import com.jonathan.catfeed.view.ViewImageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cat_grid_view) GridView catGridView;

    private FeedAdapter feedAdapter = new FeedAdapter();
    private PagingScrollListener pagingScrollListener = new PagingScrollListener();

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
                    Intent intent = new Intent(this, ViewImageActivity.class);
                    intent.putExtra(IntentKeys.IMAGE_ID, image.getId());
                    intent.putExtra(IntentKeys.IMAGE_URL, image.getUrl());
                    startActivity(intent);
                    break;
                }
        }
    }
}
