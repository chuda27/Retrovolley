package com.choirulhuda.retrovolley.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.choirulhuda.retrovolley.GlobalVariable;
import com.choirulhuda.retrovolley.R;
import com.choirulhuda.retrovolley.Request;
import com.choirulhuda.retrovolley.UserResponse;
import com.choirulhuda.retrovolley.adapter.UserAdapter;
import com.choirulhuda.retrovolley.model.User;
import com.choirulhuda.retrovolley.retrofit.MethodHTTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RetrofitActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ListView lvUser;
    private List<User> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        lvUser = findViewById(R.id.lv_user);

        //Method GET
        getUserFromAPI();

        setTitle(getString(R.string.retrofit));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.retrofit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddUserActivity.class);
                intent.putExtra(GlobalVariable.TYPE_CONN, GlobalVariable.RETROFIT);
                startActivity(intent);
                break;
            /*case R.id.action_refresh:
                getUserFromAPI();
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    public void actionRefresh(View view) {
        getUserFromAPI();
    }

    public void actionClose(View view) {
        this.finish();
    }

    private void getUserFromAPI() {
        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle(getString(R.string.retrofit));
        proDialog.setMessage("Silahkan tunggu");
        proDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GlobalVariable.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<UserResponse> call = client.getUser();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                proDialog.dismiss();
                listUser = response.body().getUser_list();
                UserAdapter userAdapter = new UserAdapter(getApplicationContext(), listUser);
                lvUser.setAdapter(userAdapter);
                lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), listUser.get(i).getUser_fullname(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }



    public void tambahanRetrofit(){
        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
    }

}