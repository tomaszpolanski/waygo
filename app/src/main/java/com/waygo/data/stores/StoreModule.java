package com.waygo.data.stores;

import android.content.ContentResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class StoreModule {

    @Provides
    @Singleton
    public NetworkRequestStatusStore provideNetworkRequestStatusStore(ContentResolver contentResolver) {
        return new NetworkRequestStatusStore(contentResolver);
    }

    @Provides
    @Singleton
    public FlightStatusStore provideFlightStatusStore(ContentResolver contentResolver) {
        return new FlightStatusStore(contentResolver);
    }

}
