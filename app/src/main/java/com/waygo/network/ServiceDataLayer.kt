package com.waygo.network

import com.waygo.data.DataLayerBase
import com.waygo.data.stores.FlightStatusStore
import com.waygo.data.stores.NetworkRequestStatusStore
import com.waygo.network.fetchers.Fetcher

import android.content.Intent
import android.net.Uri
import android.util.Log
import java.util.Collections

import rx.android.internal.Preconditions

public class ServiceDataLayer(flightStatusFetcher: Fetcher, networkRequestStatusStore: NetworkRequestStatusStore, flightStatusStore: FlightStatusStore) : DataLayerBase(networkRequestStatusStore, flightStatusStore) {

    private val fetchers: Collection<Fetcher>

    init {
        Preconditions.checkNotNull(flightStatusFetcher, "Flight Status Fetcher cannot be null.")

        fetchers = listOf(flightStatusFetcher)
    }

    public fun processIntent(intent: Intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null.")

        val contentUriString = intent.getStringExtra("contentUriString")
        if (contentUriString != null) {
            val contentUri = Uri.parse(contentUriString)
            val matchingFetcher = findFetcher(contentUri)
            if (matchingFetcher != null) {
                Log.v(TAG, "Fetcher found for " + contentUri)
                matchingFetcher.fetch(intent)
            } else {
                Log.e(TAG, "Unknown Uri " + contentUri)
            }
        } else {
            Log.e(TAG, "No Uri defined")
        }
    }

    private fun findFetcher(contentUri: Uri): Fetcher? = fetchers.firstOrNull { it.getContentUri() == contentUri }

    companion object {
        private val TAG = javaClass<ServiceDataLayer>().getSimpleName()
    }
}
