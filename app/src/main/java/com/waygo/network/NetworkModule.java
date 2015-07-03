package com.waygo.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public NetworkApi provideNetworkApi() {
        return new NetworkApi();
    }

}
