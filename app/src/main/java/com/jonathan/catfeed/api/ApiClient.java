package com.jonathan.catfeed.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jonathan.catfeed.api.models.FeedResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://thecatapi.com";

    // API parameters
    private static final String FORMAT = "XML";
    public static final int PAGE_SIZE = 15;
    private static final String IMAGE_TYPES = "jpg, png";
    private static final String IMAGE_SIZE = "small";
    private static final String API_KEY = "Mjg1NDQx";

    // OkHttp/Retrofit related constant
    private static final int DEFAULT_TIMEOUT_DURATION = 45;

    private static ApiClient apiClientInstance;
    private CatsService catsService;

    public static ApiClient get() {
        if (apiClientInstance == null) {
            synchronized (ApiClient.class) {
                if (apiClientInstance == null) {
                    apiClientInstance = new ApiClient();
                }
            }
        }

        return apiClientInstance;
    }

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

        catsService = retrofit.create(CatsService.class);
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .build();
    }

    private CatsService getCatsService() {
        return catsService;
    }

    public static Single<Response<FeedResponse>> getCatPictures() {
        return get().getCatsService()
            .getCatPictures(FORMAT, PAGE_SIZE, IMAGE_TYPES, IMAGE_SIZE, API_KEY)
            .subscribeOn(Schedulers.io());
    }
}
