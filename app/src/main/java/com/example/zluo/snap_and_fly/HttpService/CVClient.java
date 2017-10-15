package com.example.zluo.snap_and_fly.HttpService;

/**
 * Created by zluo on 10/14/17.
 */
import com.example.zluo.snap_and_fly.RequestModel.BodyUrl;
import com.example.zluo.snap_and_fly.ResponseModel.MSCVResponse;

import org.json.JSONObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Header;


public interface CVClient {
    @POST("analyze/")
    Call<MSCVResponse> getLandmark(
            @Header("ocp-apim-subscription-key") String token,
            @Header("Content-Type") String contentType,
            @Body BodyUrl url
            );
}
