package com.waygo.network.fetchers;

import com.waygo.data.stores.GitHubRepositorySearchStore;
import com.waygo.data.stores.GitHubRepositoryStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.network.NetworkApi;
import com.waygo.network.NetworkModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public final class FetcherModule {

    @Provides
    @Named("gitHubRepository")
    public Fetcher provideGitHubRepositoryFetcher(NetworkApi networkApi,
                                                  NetworkRequestStatusStore networkRequestStatusStore,
                                                  GitHubRepositoryStore gitHubRepositoryStore) {
        return new GitHubRepositoryFetcher(networkApi,
                                           networkRequestStatusStore::insertOrUpdate,
                                           gitHubRepositoryStore);
    }

    @Provides
    @Named("gitHubRepositorySearch")
    public Fetcher provideGitHubRepositorySearchFetcher(NetworkApi networkApi,
                                                        NetworkRequestStatusStore networkRequestStatusStore,
                                                        GitHubRepositoryStore gitHubRepositoryStore,
                                                        GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        return new GitHubRepositorySearchFetcher(networkApi,
                                                 networkRequestStatusStore::insertOrUpdate,
                                                 gitHubRepositoryStore,
                                                 gitHubRepositorySearchStore);
    }

}
