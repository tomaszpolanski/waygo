package com.waygo.data.stores

import com.waygo.data.base.store.ContentProviderStoreBase
import com.waygo.data.provider.NetworkRequestStatusContract
import com.waygo.pojo.NetworkRequestStatus

import android.content.ContentResolver
import android.net.Uri
import android.util.Log

import rx.android.internal.Preconditions

public class NetworkRequestStatusStore(contentResolver: ContentResolver) : ContentProviderStoreBase<NetworkRequestStatus, Int>(contentResolver, NetworkRequestStatusContract()) {

    override fun getIdFor(item: NetworkRequestStatus): Int {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.")

        return item.uri!!.hashCode()
    }

    override fun getContentUri(): Uri = NetworkRequestStatusContract.CONTENT_URI

    override fun insertOrUpdate(item: NetworkRequestStatus) {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.")

        Log.v(TAG, "insertOrUpdate(" + item.status + ", " + item.uri + ")")
        super.insertOrUpdate(item)
    }

    companion object {
        private val TAG = javaClass<NetworkRequestStatusStore>().getSimpleName()
    }
}
