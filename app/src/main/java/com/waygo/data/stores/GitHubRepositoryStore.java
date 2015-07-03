package com.waygo.data.stores;

import com.waygo.data.base.store.ContentProviderStoreBase;
import com.waygo.data.provider.GitHubRepositoryContract;
import com.waygo.pojo.GitHubRepository;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;

import rx.android.internal.Preconditions;

public class GitHubRepositoryStore extends ContentProviderStoreBase<GitHubRepository, Integer> {
    private static final String TAG = GitHubRepositoryStore.class.getSimpleName();

    public GitHubRepositoryStore(@NonNull ContentResolver contentResolver) {
        super(contentResolver, new GitHubRepositoryContract());
    }

    @NonNull
    @Override
    protected Integer getIdFor(@NonNull GitHubRepository item) {
        Preconditions.checkNotNull(item, "Github Repository cannot be null.");

        return item.getId();
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return GitHubRepositoryContract.CONTENT_URI;
    }
}
