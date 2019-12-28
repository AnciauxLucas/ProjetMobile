package com.example.mobilegenicotanciaux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.dto.UserDto;
import com.example.mobilegenicotanciaux.model.dto.UserModifyDto;
import com.example.mobilegenicotanciaux.services.UserService;
import com.example.mobilegenicotanciaux.utils.CurrentUser;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;
import com.example.mobilegenicotanciaux.utils.RetrofitFactory;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.leaf)
    public ImageView leaf;
    @BindView(R.id.input_change_pseudo)
    public TextView changePseudo;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_change_password)
    public TextView changePassword;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_change_newPassword)
    public TextView changeNewPassword;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_change_lastName)
    public TextView changeLastName;
    @NotEmpty
    @Length(min = 3, max = 30)
    @BindView(R.id.input_change_firstName)
    public TextView changeFirstName;
    @BindView(R.id.input_change_idTeam)
    public TextView changeIdTeam;
    @BindView(R.id.button_cancel)
    public Button buttonCancel;
    @BindView(R.id.button_change_user)
    public Button buttonValidate;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        Glide.with(AccountActivity.this).load(R.drawable.leaf).into(leaf);
        validator = new Validator(this);
        validator.setValidationListener(this);
        UserDto user = CurrentUser.getCurrentUser();
        changePseudo.setText(user.getPseudo());
        changeLastName.setText(user.getLastName());
        changeFirstName.setText(user.getFirstName());
        if (user.getTeacherCode() != null) changeIdTeam.setText(user.getTeacherCode());
        buttonCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        buttonValidate.setOnClickListener(v -> validator.validate());
    }

    @Override
    public void onValidationSucceeded() {
        UIUtil.hideKeyboard(this);
        if (!NetworkUtil.checkNetworkConnection(getApplicationContext())) {
            UserModifyDto user = new UserModifyDto(changePseudo.getText().toString(),
                    changePassword.getText().toString(),
                    changeNewPassword.getText().toString(),
                    changeLastName.getText().toString(),
                    changeFirstName.getText().toString());
            Retrofit retrofit = RetrofitFactory.getRetrofit();
            UserService userService = retrofit.create(UserService.class);
            Call<UserModifyDto> call = userService.modifyUser(user);
            call.enqueue(new Callback<UserModifyDto>() {
                @Override
                public void onResponse(Call<UserModifyDto> call, Response<UserModifyDto> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.successModify), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AccountActivity.this, LogInActivity.class);
                        startActivity(intent);
                    } else {
                        if (response.code() == 400) {
                            changePassword.setError(getString(R.string.wrongPassword));
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.tokenExpired), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserModifyDto> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getString(R.string.errorCallAPI), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            NetworkUtil.prepareToast(getApplicationContext()).show();
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
