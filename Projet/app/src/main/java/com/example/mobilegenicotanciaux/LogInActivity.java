package com.example.mobilegenicotanciaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.leaf)
    public ImageView leaf;
    @BindView(R.id.input_username)
    public EditText username;
    @BindView(R.id.input_password)
    public EditText password;
    @BindView(R.id.no_match)
    public TextView noMatch;
    @BindView(R.id.button_logIn)
    public Button buttonLogIn;
    @BindView(R.id.button_register)
    public Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        Glide.with(LogInActivity.this).load(R.drawable.leaf).into(leaf);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean error = false;
                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError(getResources().getString(R.string.errorUsername));
                    error = true;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError(getResources().getString(R.string.errorPassword));
                    error = true;
                }
                if (!error) {
                    if (username.getText().toString().equals("etu") && password.getText().toString().equals("1234")) {
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        noMatch.setText(getResources().getString(R.string.noMatch));
                    }
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
