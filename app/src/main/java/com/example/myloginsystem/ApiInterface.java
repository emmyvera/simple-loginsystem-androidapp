package com.example.myloginsystem;


import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    Call<User> performRegistration(@Field("username") String username,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("confirmPassword") String confirmPassword,
                                   @Field("phoneNo") String phoneNo);

    @FormUrlEncoded
    @POST("login")
    Call<User> performLogin(@Field("username") String username,
                            @Field("password") String password);
    @FormUrlEncoded
    @POST("userdetails")
    Call<User> userDetails(@Field("id") String id,
                           @Header("auth-token") String token);

    @Multipart
    @POST("up/")
    Call<RequestBody> uploadPics(@Header("auth-token") String token,
                                 @Part MultipartBody.Part part,
                                 @Part("image") RequestBody requestBody);

}
