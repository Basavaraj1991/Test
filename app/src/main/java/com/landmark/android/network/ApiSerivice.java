package com.landmark.android.network;

import com.landmark.android.network.response.ProductResModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSerivice {

    //Need to add the api's in the interface
    @GET("assignment.json")
    Call<ProductResModel> getProductDetails();
}
