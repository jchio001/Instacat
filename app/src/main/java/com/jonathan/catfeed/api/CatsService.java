package com.jonathan.catfeed.api;

import com.jonathan.catfeed.api.models.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatsService {
    @GET("/api/images/get")
    Call<FeedResponse> getCatPictures(@Query("format") String format,
                                      @Query("results_per_page") int pageSize,
                                      @Query("type") String imageTypes,
                                      @Query("size") String imageSize,
                                      @Query("api_key") String apiKey);
}
