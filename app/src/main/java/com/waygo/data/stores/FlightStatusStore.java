package com.waygo.data.stores;

import com.waygo.data.base.store.ContentProviderStoreBase;
import com.waygo.data.provider.FlightStatusContract;
import com.waygo.pojo.flightstatus.Flight;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;

public class FlightStatusStore extends ContentProviderStoreBase<Flight, String> {

    private static final String TAG = FlightStatusStore.class.getSimpleName();

    public FlightStatusStore(@NonNull ContentResolver contentResolver) {
        super(contentResolver, new FlightStatusContract());
    }

    @NonNull
    @Override
    protected String getIdFor(@NonNull Flight item) {
        return item.getId();
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return FlightStatusContract.CONTENT_URI;
    }
}
