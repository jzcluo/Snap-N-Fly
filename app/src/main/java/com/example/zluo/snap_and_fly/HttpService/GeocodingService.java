package com.example.zluo.snap_and_fly.HttpService;

import com.example.zluo.snap_and_fly.ResponseModel.GeocodingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zluo on 10/14/17.
 */

public interface GeocodingService {
    @GET("json?")
    Call<GeocodingResponse> getCityName(
           @Query("address") String address,
           @Query("key") String key
    );
}
