package com.example.myloginsystem;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    Call<User> performRegistration(@Field("username") String username,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("confirmPassword") String confirmPassword,
                                   @Field("phoneNo") String phoneNo);

    @POST("login")
    Call<User> performLogin(@Query("username") String username,
                            @Query("password") String password);

    @GET("userdetails")
    Call<User> userDetails(String id);

}
