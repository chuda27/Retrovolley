package com.choirulhuda.retrovolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.choirulhuda.retrovolley.adapter.UserAdapter;
import com.choirulhuda.retrovolley.model.User;
import com.choirulhuda.retrovolley.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

public class RetrofitActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ListView lvUser;
    private List<User> listUser = new ArrayList<>();
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        lvUser = findViewById(R.id.lv_user);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setVisibility(View.INVISIBLE);
        getUserFromAPI();

        setTitle("Retrofit");
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
            case R.id.action_refresh:
                getUserFromAPI();
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                break;
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
        ApiService.endPoint().getUser()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
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

}