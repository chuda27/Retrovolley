package com.choirulhuda.retrovolley.retrofit;

import com.choirulhuda.retrovolley.Request;
import com.choirulhuda.retrovolley.UserResponse;
import com.choirulhuda.retrovolley.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MethodHTTP {

    @GET("User_Registration.php")
    Call<UserResponse> getUser();

    @POST("User_Registration.php")
    Call<Request> sendUser(@Body User user);
}
