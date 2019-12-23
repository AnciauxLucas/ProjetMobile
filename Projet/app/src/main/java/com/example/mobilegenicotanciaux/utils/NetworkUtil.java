package com.example.mobilegenicotanciaux.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

import com.example.mobilegenicotanciaux.R;

public class NetworkUtil {

    public static Boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        return network == null;
    }

    public static Toast prepareToast(Context context) {
        return Toast.makeText(context, context.getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT);
    }
}
