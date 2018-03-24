package com.jonathan.catfeed.data;

import android.os.Handler;

import com.jonathan.catfeed.api.ApiClient;
import com.jonathan.catfeed.api.models.FeedResponse;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.feed.DelayManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedManager {

    public interface FeedListener {
        void onSuccess(List<Image> images);
        void onFailure(Throwable t);
    }

    private static final int HTTP_STATUS_OK = 200;

    private static FeedManager instance;

    private FeedListener listener;

    private Handler handler = new Handler();

    private final Callback<FeedResponse> feedCallback = new Callback<FeedResponse>() {
        @Override
        public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
            if (response.code() == HTTP_STATUS_OK) {
                if (listener != null) {
                    listener.onSuccess(response.body().getFeedData().getImages().getImageList());
                }
            } else {
                if (listener != null) {
                    listener.onFailure(new Exception("Network request failed"));
                }
            }
        }

        @Override
        public void onFailure(Call<FeedResponse> call, Throwable t) {
            if (listener != null) {
                listener.onFailure(t);
            }
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

    public void listen(FeedListener listener) {
        this.listener = listener;
    }

    public void requestImages() {
        handler.postDelayed(() ->
            ApiClient.getCatPictures().enqueue(get().feedCallback),
            DelayManager.get().calculateDelay());
    }

    public static void removeListener() {
        get().listen(null);
    }
}
