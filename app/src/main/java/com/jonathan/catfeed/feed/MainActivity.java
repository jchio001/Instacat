package com.jonathan.catfeed.feed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.data.FeedManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cat_grid_view) GridView catGridView;

    private FeedAdapter feedAdapter = new FeedAdapter();
    private PagingScrollListener pagingScrollListener = new PagingScrollListener();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Observer<List<Image>> imageObserver = new Observer<List<Image>>() {
        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(List<Image> images) {
            feedAdapter.addImages(images);
            pagingScrollListener.setLoading(false);
        }

        @Override
        public void onError(Throwable e) {
            feedAdapter.handleFailedRequest();
            pagingScrollListener.setLoading(false);
        }

        @Override
        public void onComplete() {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        catGridView.setAdapter(feedAdapter);
        catGridView.setOnScrollListener(pagingScrollListener);

        FeedManager.subscribeToFeed(imageObserver);
        FeedManager.requestImages();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
