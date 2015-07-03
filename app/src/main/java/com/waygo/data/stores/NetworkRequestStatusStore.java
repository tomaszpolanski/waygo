package com.waygo.data.stores;

import com.waygo.data.base.store.ContentProviderStoreBase;
import com.waygo.data.provider.NetworkRequestStatusContract;
import com.waygo.pojo.NetworkRequestStatus;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import rx.android.internal.Preconditions;

public class NetworkRequestStatusStore extends ContentProviderStoreBase<NetworkRequestStatus, Integer> {
    private static final String TAG = NetworkRequestStatusStore.class.getSimpleName();

    public NetworkRequestStatusStore(@NonNull ContentResolver contentResolver) {
        super(contentResolver, new NetworkRequestStatusContract());
    }

    @NonNull
    @Override
    protected Integer getIdFor(@NonNull NetworkRequestStatus item) {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.");

        return item.getUri().hashCode();
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return NetworkRequestStatusContract.CONTENT_URI;
    }

    @Nullable
    @Override
    protected NetworkRequestStatus query(@NonNull Uri uri) {
        return super.query(uri);
    }

    @Override
    public void insertOrUpdate(@NonNull NetworkRequestStatus item) {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.");

        Log.v(TAG, "insertOrUpdate(" + item.getStatus() + ", " + item.getUri() + ")");
        super.insertOrUpdate(item);
    }
}
