package com.choirulhuda.retrovolley;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.choirulhuda.retrovolley.retrofit.ApiService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserFromAPI();
    }

    private void getUserFromAPI() {
        ApiService.endPoint().getUser()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.body().getCode() == 200) {
                            List<UserResponse.User> user_list = response.body().getUser_list();
                            Log.d(TAG, user_list.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
    }
}