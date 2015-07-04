package com.waygo.network;

import com.waygo.data.DataLayerBase;
import com.waygo.data.stores.FlightStatusStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.network.fetchers.Fetcher;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;

import rx.android.internal.Preconditions;

public class ServiceDataLayer extends DataLayerBase {
    private static final String TAG = ServiceDataLayer.class.getSimpleName();

    @NonNull
    final private Collection<Fetcher> fetchers;

    public ServiceDataLayer(@NonNull Fetcher flightStatusFetcher,
                            @NonNull NetworkRequestStatusStore networkRequestStatusStore,
                            @NonNull FlightStatusStore flightStatusStore) {
        super(networkRequestStatusStore, flightStatusStore);

        Preconditions.checkNotNull(flightStatusFetcher, "Flight Status Fetcher cannot be null.");

        fetchers = Collections.singletonList(flightStatusFetcher);
    }

    public void processIntent(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null.");

        final String contentUriString = intent.getStringExtra("contentUriString");
        if (contentUriString != null) {
            final Uri contentUri = Uri.parse(contentUriString);
            Fetcher matchingFetcher = findFetcher(contentUri);
            if (matchingFetcher != null) {
                Log.v(TAG, "Fetcher found for " + contentUri);
                matchingFetcher.fetch(intent);
            } else {
                Log.e(TAG, "Unknown Uri " + contentUri);
            }
        } else {
            Log.e(TAG, "No Uri defined");
        }
    }

    @Nullable
    private Fetcher findFetcher(@NonNull Uri contentUri) {
        Preconditions.checkNotNull(contentUri, "Content URL cannot be null.");

        for (Fetcher fetcher : fetchers) {
            if (fetcher.getContentUri().equals(contentUri)) {
                return fetcher;
            }
        }
        return null;
    }
}
