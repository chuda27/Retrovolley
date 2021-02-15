package com.choirulhuda.retrovolley.retrofit;

import com.choirulhuda.retrovolley.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoint {
    @GET("User_Registration.php")
    Call<UserResponse> getUser();
}
