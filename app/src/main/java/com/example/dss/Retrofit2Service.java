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
import retrofit2.http.Query;


public interface Retrofit2Service {

    @FormUrlEncoded
    @POST("userdrug")
    Call<Retrofit2TestModel> postdata(  @Field("no")Integer No,
                                        @Field("drug")String drug,
                                        @Field("flag") String flag,
                                        @Field("regdate") String regdate);


    @FormUrlEncoded
    @POST("dssuser/userCheck")
    Call<Retrofit2UserModel> userCheck( @Field("email")String drug,
                                         @Field("pwd") String flag);

    @GET("send/")
    Call<Retrofit2Fcm> getSend(@Query("bodys") String bodys);
}
