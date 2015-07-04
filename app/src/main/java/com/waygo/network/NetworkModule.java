package com.waygo.network;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class NetworkModule {

    @Provides
    @Singleton
    @Named("unauthenticated")
    public LufthansaAccountService provideLufthansaAccountService() {
        return ServiceGenerator
                .createService(LufthansaAccountService.class,
                               LufthansaAccountService.BASE_URL);
    }

    @Provides
    @Singleton
    @Named("authenticated")
    public LufthansaAccountService provideAuthenticatedLufthansaAccountService(
            AccessToken accessToken) {
        return ServiceGenerator
                .createService(LufthansaAccountService.class,
                               LufthansaAccountService.BASE_URL,
                               accessToken);
    }

    @Provides
    @Singleton
    public AccessToken provideAccessToken(
            @Named("unauthenticated") LufthansaAccountService accountService) {
        try {
            return accountService.getAccessToken("mphx5vxg66a653d7ct4bzdst",
                                                 "q7wHAfbKhj",
                                                 "client_credentials")
                                 .toBlocking()
                                 .single();
        } catch (Exception e) {
            return new AccessToken("", "", 0L);
        }
    }

}
