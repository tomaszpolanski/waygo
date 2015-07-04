package com.waygo.data;

import com.waygo.data.stores.FlightStatusStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.data.stores.StoreModule;
import com.waygo.injections.ForApplication;
import com.waygo.network.ServiceDataLayer;
import com.waygo.network.fetchers.Fetcher;
import com.waygo.network.fetchers.FetcherModule;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = { FetcherModule.class, StoreModule.class })
public final class DataStoreModule {

    @Provides
    public DataLayer.GetFlightStatus provideGetFlightStatus(DataLayer dataLayer) {
        return dataLayer::getFlightStatus;
    }

    @Provides
    public DataLayer.FetchAndGetGetFlightStatus provideFetchAndGetGetFlightStatus(DataLayer dataLayer) {
        return dataLayer::fetchAndGetFlightStatus;
    }

    @Provides
    @Singleton
    public DataLayer provideApplicationDataLayer(@ForApplication Context context,
                                                 NetworkRequestStatusStore networkRequestStatusStore,
                                                 FlightStatusStore flightStatusStore) {
        return new DataLayer(context, networkRequestStatusStore, flightStatusStore);
    }

    @Provides
    @Singleton
    public ServiceDataLayer provideServiceDataLayer(@Named("flightStatus") Fetcher flightStatusFetcher,
                                                    NetworkRequestStatusStore networkRequestStatusStore,
                                                    FlightStatusStore flightStatusStore) {
        return new ServiceDataLayer(flightStatusFetcher,
                                    networkRequestStatusStore,
                                    flightStatusStore);
    }

}
