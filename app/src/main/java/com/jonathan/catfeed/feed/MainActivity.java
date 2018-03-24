package com.jonathan.catfeed.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.data.FeedManager;
import com.jonathan.catfeed.data.FeedManager.FeedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
}
