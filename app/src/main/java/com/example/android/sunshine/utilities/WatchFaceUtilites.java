package com.example.android.sunshine.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 *
 * Created by Semmiverian on 3/26/17.
 */

public class WatchFaceUtilites {
    private static final String MAX_TEMP = "com.example.android.sunshine.max_temp";
    private static final String MIN_TEMP = "com.example.android.sunshine.min_temp";
    /**
     * Send Data to Watch Face
     *
     * @param mContext context to set the Google Api Client
     * @param weatherValue Weather Value that being fetch from the server
     */
    public static void sendDataToWatch(Context mContext, ContentValues weatherValue) {
        Log.d("Watch", "sendDataToWatch: " + weatherValue);


        GoogleApiClient  mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.d("Watch", "onConnected: connect ");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sync");
        putDataMapReq.getDataMap().putString(MAX_TEMP, "111");
        putDataMapReq.getDataMap().putString(MIN_TEMP, "100");
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();

        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                if (dataItemResult.getStatus().isSuccess()) {
                    Log.d("Watch", "onResult: Berhasil kirim");
                } else {
                    Log.e("Watch", "onResult: Failed");
                }
            }
        });

    }
}
