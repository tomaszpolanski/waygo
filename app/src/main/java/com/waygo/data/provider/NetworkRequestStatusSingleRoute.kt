package com.waygo.data.provider

import com.waygo.data.base.contract.SerializedJsonContract
import com.waygo.data.base.route.DatabaseRouteBase

import android.net.Uri

public class NetworkRequestStatusSingleRoute(tableName: String) : DatabaseRouteBase(tableName) {

    override fun getMimeType(): String = MULTIPLE_MIME_TYPE
    override fun getDefaultSortOrder(): String = SerializedJsonContract.ID + " ASC"
    override fun getPath(): String = getTableName() + "/*"

    override fun getWhere(uri: Uri): String =
            "${SerializedJsonContract.ID} = ${Integer.parseInt(uri.getLastPathSegment())}"

    companion object {
        private val MULTIPLE_MIME_TYPE = "vnd.android.cursor.dir/vnd.waygo.android.networkrequeststatus"
    }
}
