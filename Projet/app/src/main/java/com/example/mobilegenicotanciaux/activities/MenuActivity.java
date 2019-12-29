package com.example.mobilegenicotanciaux.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.Formation;
import com.example.mobilegenicotanciaux.model.TrainingSchool;
import com.example.mobilegenicotanciaux.services.FormationService;
import com.example.mobilegenicotanciaux.utils.CurrentUser;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;
import com.example.mobilegenicotanciaux.utils.RetrofitFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    @BindView(R.id.button_qr_code)
    public Button buttonQr;
    @BindView(R.id.button_formations)
    public Button buttonFormations;
    @BindView(R.id.button_account)
    public Button buttonAccount;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        buttonQr.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MenuActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
            Context context = getApplicationContext();
            if (NetworkUtil.checkNetworkConnection(context)) {
                Toast toast = NetworkUtil.prepareToast(context);
                toast.show();
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !NetworkUtil.checkNetworkConnection(context)) {
                Intent intent = new Intent(MenuActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
        buttonFormations.setOnClickListener(v -> {
            if (!NetworkUtil.checkNetworkConnection(getApplicationContext())) {
                Retrofit retrofit = RetrofitFactory.getRetrofit();
                FormationService formationService = retrofit.create(FormationService.class);
                Call<ArrayList<TrainingSchool>> call = formationService.getFormations();
                call.enqueue(new Callback<ArrayList<TrainingSchool>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TrainingSchool>> call, Response<ArrayList<TrainingSchool>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<TrainingSchool> formations = response.body();
                            Intent intent = new Intent(MenuActivity.this, FormationActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("formations", formations);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.tokenExpired), Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TrainingSchool>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getString(R.string.errorCallAPI), Toast.LENGTH_SHORT);
                    }
                });
            } else {
                NetworkUtil.prepareToast(getApplicationContext()).show();
            }
        });
        buttonAccount.setOnClickListener(v -> {
            if (CurrentUser.getCurrentUser() == null) {
                Context context = getApplicationContext();
                if (!NetworkUtil.checkNetworkConnection(context)) {
                    if (CurrentUser.getCurrentUserFromApi()) {
                        Toast.makeText(context, getString(R.string.errorCallAPI), Toast.LENGTH_SHORT);
                    } else {
                        Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
                        startActivity(intent);
                    }
                } else {
                    NetworkUtil.prepareToast(context).show();
                }
            } else {
                Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finishAffinity();
        } else {
            backToast = Toast.makeText(getBaseContext(), getString(R.string.closeApp), Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
