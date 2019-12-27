package com.example.mobilegenicotanciaux.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.Planting;
import com.example.mobilegenicotanciaux.services.PlantingService;
import com.example.mobilegenicotanciaux.utils.NetworkUtil;
import com.example.mobilegenicotanciaux.utils.RetrofitFactory;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScanActivity extends AppCompatActivity {

    @BindView(R.id.camera)
    public SurfaceView camera;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        camera.setForegroundTintList(ColorStateList.valueOf(Color.BLACK));
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();
        camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    if (qrCodes.valueAt(0).displayValue.matches("https://qrco.de/U-Garden_[0-9]+")) {
                        if (camera.getForegroundTintList() != ColorStateList.valueOf(Color.GREEN)) {
                            camera.setForegroundTintList(ColorStateList.valueOf(Color.GREEN));
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            String[] urlSplit = qrCodes.valueAt(0).displayValue.split("_");
                            Integer id = Integer.parseInt(urlSplit[urlSplit.length - 1]);
                            if (!NetworkUtil.checkNetworkConnection(getApplicationContext())) {
                                Retrofit retrofit = RetrofitFactory.getRetrofit();
                                PlantingService plantingService = retrofit.create(PlantingService.class);
                                Call<Planting> call = plantingService.getPlanting(id);
                                call.enqueue(new Callback<Planting>() {
                                    @Override
                                    public void onResponse(Call<Planting> call, Response<Planting> response) {
                                        if (response.isSuccessful()) {
                                            Intent intent = new Intent(ScanActivity.this, PlantationActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("planting", response.body());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else {
                                            camera.post(() -> {
                                                recreate();
                                                Toast.makeText(getApplicationContext(), getString(R.string.tokenExpired), Toast.LENGTH_LONG).show();
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Planting> call, Throwable t) {
                                        camera.post(() -> {
                                            recreate();
                                            Toast.makeText(getApplicationContext(), getString(R.string.errorCallAPI), Toast.LENGTH_LONG).show();
                                        });
                                    }
                                });
                            } else {
                                camera.post(() -> {
                                    recreate();
                                    NetworkUtil.prepareToast(getApplicationContext()).show();
                                });
                            }
                        }
                    }
                }
            }
        });
    }
}