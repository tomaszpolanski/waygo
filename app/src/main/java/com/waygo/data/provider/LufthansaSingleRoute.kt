package com.waygo.data.provider

import android.net.Uri

import com.waygo.data.base.contract.SerializedJsonContract
import com.waygo.data.base.route.DatabaseRouteBase

import rx.android.internal.Preconditions

public class LufthansaSingleRoute(tableName: String) : DatabaseRouteBase(tableName) {

    override fun getDefaultSortOrder(): String = SerializedJsonContract.ID + " ASC"

    override fun getMimeType(): String = SINGLE_MIME_TYPE

    override fun getWhere(uri: Uri): String = "${SerializedJsonContract.ID} = \"${uri.getLastPathSegment()}\""

    override fun getPath(): String = getTableName() + "/*"

    companion object {
        private val SINGLE_MIME_TYPE = "vnd.android.cursor.item/vnd.waygo.android.repository"
    }
}
