package com.example.mobilegenicotanciaux.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    @BindView(R.id.button_qr_code)
    public Button buttonQr;
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
