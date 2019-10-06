package com.example.dss;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Retrofit2Service {

    @FormUrlEncoded
    @POST("userdrug")
    Call<Retrofit2TestModel> postdata(  @Field("no")Integer No,
                                        @Field("drug")String drug,
                                        @Field("flag") String flag,
                                        @Field("regdate") String regdate);


}
