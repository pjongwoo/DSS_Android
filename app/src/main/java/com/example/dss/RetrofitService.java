package com.example.dss;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("drug/findName/{name}")
    Call<JsonArray> getListRepos(@Path("name") String id);
}
