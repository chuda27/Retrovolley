package com.choirulhuda.retrovolley.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choirulhuda.retrovolley.GlobalVariable;
import com.choirulhuda.retrovolley.R;
import com.choirulhuda.retrovolley.Request;
import com.choirulhuda.retrovolley.model.User;
import com.choirulhuda.retrovolley.retrofit.MethodHTTP;

public class AddUserActivity extends AppCompatActivity {

    private EditText edtFullname, edtEmail, edtPassword;
    private Button btnSubmit;
    private String typeConn = "";

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        edtFullname = findViewById(R.id.edt_fullname);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSubmit = findViewById(R.id.btn_submit);

        setTitle(getString(R.string.tambah_user));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            typeConn = extras.getString(GlobalVariable.TYPE_CONN);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void actionSubmit(View view) {
        boolean isInputValid = false;

        if (edtFullname.getText().toString().isEmpty()) {
            edtFullname.setError(getString(R.string.tidak_boleh_kosong));
            edtFullname.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError(getString(R.string.tidak_boleh_kosong));
            edtEmail.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getString(R.string.tidak_boleh_kosong));
            edtPassword.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (isInputValid) {
            User user = new User();
            user.setUser_fullname(edtFullname.getText().toString());
            user.setUser_email(edtEmail.getText().toString());
            user.setUser_password(edtPassword.getText().toString());
            if (typeConn.equalsIgnoreCase(GlobalVariable.RETROFIT))
                submitByRetrofit(user);
            else submitByVolley(user);
        }


    }

    public void submitByRetrofit(User user){
        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle(getString(R.string.retrofit));
        proDialog.setMessage("Sedang disubmit");
        proDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GlobalVariable.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<Request> call = client.sendUser(user);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                proDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getCode() == 201) {
                        Toast.makeText(getApplicationContext(), "Response : "+response.body().getStatus(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (response.body().getCode() == 406){
                        Toast.makeText(getApplicationContext(), "Response : "+response.body().getStatus(),
                                Toast.LENGTH_SHORT).show();
                        edtEmail.requestFocus();
                    } else {
                        Toast.makeText(getApplicationContext(), "Response : "+response.body().getStatus(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                Log.e(TAG, "Error : "+response.message());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                proDialog.dismiss();
                Log.e(TAG, "Error2 : "+t.getMessage());
            }
        });
    }

    public void submitByVolley(User user){

    }
}