package com.choirulhuda.retrovolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.choirulhuda.retrovolley.activity.RetrofitActivity;
import com.choirulhuda.retrovolley.retrofit.ApiService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private SharedPreferences pref;
    public static String PREFERENCE_NAME = "Retrovolley";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id) {
            case R.id.action_url:
                View urlView = getLayoutInflater().inflate(R.layout.prompt_url, null);
                EditText edtBaseURL = urlView.findViewById(R.id.edt_base_url);
                String globalURL = pref.getString(ApiService.BASE_URL, null);

                if (globalURL != null) {
                    edtBaseURL.setText(globalURL);
                }
                new AlertDialog.Builder(this)
                        .setTitle("Base URL")
                        .setView(urlView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String globalURL = edtBaseURL.getText().toString();
                                SharedPreferences.Editor prefEditor = pref.edit();
                                prefEditor.putString(ApiService.BASE_URL, globalURL);
                                prefEditor.apply();
                            }
                        }). setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actionRetrofit(View view) {
        Intent retrofit = new Intent(this, RetrofitActivity.class);
        startActivity(retrofit);
    }
}