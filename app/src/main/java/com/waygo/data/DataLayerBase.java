package com.waygo.data;

import com.waygo.data.stores.FlightStatusStore;
import com.waygo.data.stores.NetworkRequestStatusStore;

import android.support.annotation.NonNull;

import rx.android.internal.Preconditions;

abstract public class DataLayerBase {
    protected final NetworkRequestStatusStore networkRequestStatusStore;
    protected final FlightStatusStore flightStatusStore;

    public DataLayerBase(@NonNull NetworkRequestStatusStore networkRequestStatusStore,
                         @NonNull FlightStatusStore flightStatusStore) {
        Preconditions.checkNotNull(networkRequestStatusStore,
                                   "Network Request Status Store cannot be null.");
        Preconditions.checkNotNull(flightStatusStore,
                                   "Flight Status Store cannot be null.");

        this.networkRequestStatusStore = networkRequestStatusStore;
        this.flightStatusStore = flightStatusStore;
    }
}
