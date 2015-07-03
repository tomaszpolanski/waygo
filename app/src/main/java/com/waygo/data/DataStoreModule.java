package com.waygo.data;

import com.waygo.data.stores.GitHubRepositorySearchStore;
import com.waygo.data.stores.GitHubRepositoryStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.data.stores.StoreModule;
import com.waygo.data.stores.UserSettingsStore;
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
    public DataLayer.GetUserSettings provideGetUserSettings(DataLayer dataLayer) {
        return dataLayer::getUserSettings;
    }

    @Provides
    public DataLayer.SetUserSettings provideSetUserSettings(DataLayer dataLayer) {
        return dataLayer::setUserSettings;
    }

    @Provides
    public DataLayer.FetchAndGetGitHubRepository provideFetchAndGetGitHubRepository(DataLayer dataLayer) {
        return dataLayer::fetchAndGetGitHubRepository;
    }

    @Provides
    public DataLayer.GetGitHubRepositorySearch provideGetGitHubRepositorySearch(DataLayer dataLayer) {
        return dataLayer::fetchAndGetGitHubRepositorySearch;
    }

    @Provides
    public DataLayer.GetGitHubRepository provideGetGitHubRepository(DataLayer dataLayer) {
        return dataLayer::getGitHubRepository;
    }

    @Provides
    @Singleton
    public DataLayer provideApplicationDataLayer(@ForApplication Context context,
                                                 UserSettingsStore userSettingsStore,
                                                 NetworkRequestStatusStore networkRequestStatusStore,
                                                 GitHubRepositoryStore gitHubRepositoryStore,
                                                 GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        return new DataLayer(context, userSettingsStore, networkRequestStatusStore, gitHubRepositoryStore, gitHubRepositorySearchStore);
    }

    @Provides
    @Singleton
    public ServiceDataLayer provideServiceDataLayer(@Named("gitHubRepository")Fetcher gitHubRepositoryFetcher,
                                                    @Named("gitHubRepositorySearch") Fetcher gitHubRepositorySearchFetcher,
                                                    NetworkRequestStatusStore networkRequestStatusStore,
                                                    GitHubRepositoryStore gitHubRepositoryStore,
                                                    GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        return new ServiceDataLayer(gitHubRepositoryFetcher,
                                    gitHubRepositorySearchFetcher,
                                    networkRequestStatusStore,
                                    gitHubRepositoryStore,
                                    gitHubRepositorySearchStore);
    }

}
