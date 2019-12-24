package com.example.mobilegenicotanciaux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.Jwt;
import com.example.mobilegenicotanciaux.model.User;
import com.example.mobilegenicotanciaux.services.JwtService;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.*;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.leaf)
    public ImageView leaf;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_username)
    public EditText username;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_password)
    public EditText password;
    @BindView(R.id.no_match)
    public TextView noMatch;
    @BindView(R.id.button_logIn)
    public Button buttonLogIn;
    @BindView(R.id.button_register)
    public Button buttonRegister;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        Glide.with(LogInActivity.this).load(R.drawable.leaf).into(leaf);
        validator = new Validator(this);
        validator.setValidationListener(this);
        buttonLogIn.setOnClickListener(v -> {
            validator.validate();
        });
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onValidationSucceeded() {
        if (!NetworkUtil.checkNetworkConnection(getApplicationContext())) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JwtService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JwtService jwtService = retrofit.create(JwtService.class);
            Call<User> call = jwtService.getToken(new Jwt(username.getText().toString(), password.getText().toString()));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        //Stock
                        Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                        startActivity(intent);
                    } else {
                        noMatch.setText(getString(R.string.noMatch));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getString(R.string.errorCallAPI), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast toast = NetworkUtil.prepareToast(getApplicationContext());
            toast.show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            ((EditText) view).setError(getString(R.string.error));
        }
    }
}