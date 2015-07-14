package com.waygo.data.stores

import com.waygo.data.base.store.ContentProviderStoreBase
import com.waygo.data.provider.FlightStatusContract
import com.waygo.pojo.flightstatus.Flight

import android.content.ContentResolver
import android.net.Uri

public class FlightStatusStore(contentResolver: ContentResolver) : ContentProviderStoreBase<Flight, String>(contentResolver, FlightStatusContract()) {
    override fun getIdFor(item: Flight): String = item.getId()
    override public fun getContentUri(): Uri = FlightStatusContract.CONTENT_URI
}
