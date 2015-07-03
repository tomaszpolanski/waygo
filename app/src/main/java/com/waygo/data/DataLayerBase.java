package com.waygo.data;

import com.waygo.data.stores.GitHubRepositorySearchStore;
import com.waygo.data.stores.GitHubRepositoryStore;
import com.waygo.data.stores.NetworkRequestStatusStore;

import android.support.annotation.NonNull;

import rx.android.internal.Preconditions;

abstract public class DataLayerBase {
    protected final NetworkRequestStatusStore networkRequestStatusStore;
    protected final GitHubRepositoryStore gitHubRepositoryStore;
    protected final GitHubRepositorySearchStore gitHubRepositorySearchStore;

    public DataLayerBase(@NonNull NetworkRequestStatusStore networkRequestStatusStore,
                         @NonNull GitHubRepositoryStore gitHubRepositoryStore,
                         @NonNull GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        Preconditions.checkNotNull(networkRequestStatusStore,
                                   "Network Request Status Store cannot be null.");
        Preconditions.checkNotNull(gitHubRepositoryStore,
                                   "GitHub Repository Store cannot be null.");
        Preconditions.checkNotNull(gitHubRepositorySearchStore,
                                   "GitHub Repository Search Store cannot be null.");

        this.networkRequestStatusStore = networkRequestStatusStore;
        this.gitHubRepositoryStore = gitHubRepositoryStore;
        this.gitHubRepositorySearchStore = gitHubRepositorySearchStore;
    }
}
