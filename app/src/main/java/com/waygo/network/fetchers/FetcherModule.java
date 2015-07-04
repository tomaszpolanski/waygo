package com.waygo.network.fetchers;

import com.waygo.data.stores.FlightStatusStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.network.LufthansaAccountService;
import com.waygo.network.NetworkModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public final class FetcherModule {

    @Provides
    @Named("flightStatus")
    public Fetcher provideFlightStatusFetcher(@Named("authenticated") LufthansaAccountService networkApi,
                                              NetworkRequestStatusStore networkRequestStatusStore,
                                              FlightStatusStore flightStatusStore) {
        return new FlightStatusFetcher(networkApi,
                                       networkRequestStatusStore::insertOrUpdate,
                                       flightStatusStore);
    }

}
