package com.waygo.data.base.route

import android.net.Uri

import rx.functions.Action1

public interface DatabaseRoute {
    public fun getPath(): String
    public fun getTableName(): String
    public fun getDefaultSortOrder(): String
    public fun getWhere(uri: Uri): String
    public fun getMimeType(): String
    public fun notifyChange(uri: Uri, notifyChange: (Uri) -> Unit)
}
