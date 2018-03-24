package com.jonathan.catfeed.data;

import android.util.Log;

import com.jonathan.catfeed.api.ApiClient;
import com.jonathan.catfeed.api.models.FeedResponse;
import com.jonathan.catfeed.api.models.Image;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Response;

public class FeedManager {

    private static final String LOG_TAG = "FeedManager";

    private static FeedManager instance;

    private PublishSubject<List<Image>> imageSubject = PublishSubject.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SingleObserver<Response<FeedResponse>> feedObserver =
        new SingleObserver<Response<FeedResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Response<FeedResponse> response) {
                Log.i(LOG_TAG, "We did it!");
                imageSubject.onNext(response.body().getFeedData().getImages().getImageList());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, e.getMessage());
                imageSubject.onError(e);
            }
        };

    public static FeedManager get() {
        if (instance == null) {
            synchronized (FeedManager.class) {
                if (instance == null) {
                    instance = new FeedManager();
                }
            }
        }

        return instance;
    }

    public static void requestImages() {
        ApiClient.getCatPictures()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(get().feedObserver);
    }

    public static void subscribeToFeed(Observer<List<Image>> imagesObserver) {
        get().imageSubject.subscribeWith(imagesObserver);
    }
}
