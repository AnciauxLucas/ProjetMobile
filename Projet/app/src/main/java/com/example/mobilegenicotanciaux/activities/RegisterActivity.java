package com.example.mobilegenicotanciaux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.User;
import com.example.mobilegenicotanciaux.services.UserService;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {

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
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_firstName)
    public EditText firstName;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_lastName)
    public EditText lastName;
    @NotEmpty
    @Length(min = 16, max = 30)
    @BindView(R.id.input_idTeam)
    public EditText idTeam;
    @BindView(R.id.button)
    public Button button;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Glide.with(RegisterActivity.this).load(R.drawable.leaf).into(leaf);
        validator = new Validator(this);
        validator.setValidationListener(this);
        button.setOnClickListener(v -> {
            validator.validate();
        });
    }

    @Override
    public void onValidationSucceeded() {
        if (!NetworkUtil.checkNetworkConnection(getApplicationContext())) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UserService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService userService = retrofit.create(UserService.class);
            Call<User> call = userService.addUser(idTeam.getText().toString(), new User(username.getText().toString(),
                    password.getText().toString(),
                    firstName.getText().toString(),
                    lastName.getText().toString()));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.userCreated), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                        startActivity(intent);
                    } else {
                        if (response.code() == 400) {
                            idTeam.setError(getString(R.string.errorAccessCode));
                        }
                        if (response.code() == 500) {
                            username.setError(getString(R.string.usernameExisting));
                        }
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
            if (view == idTeam) {
                ((EditText) view).setError(getString(R.string.errorIdTeam));
            } else {
                ((EditText) view).setError(getString(R.string.error));
            }
        }
    }
}
