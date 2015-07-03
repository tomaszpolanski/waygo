package com.waygo.network;

import com.waygo.WaygoApplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import javax.inject.Inject;

public class NetworkService extends Service {
    private static final String TAG = NetworkService.class.getSimpleName();

    @Inject
    ServiceDataLayer serviceDataLayer;

    @Override
    public void onCreate() {
        super.onCreate();

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent != null) {
            serviceDataLayer.processIntent(intent);
        } else {
            Log.d(TAG, "Intent was null, no action taken");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
