package com.waygo.data.base.route

import android.net.Uri

import rx.android.internal.Preconditions
import rx.functions.Action1

public abstract class DatabaseRouteBase(protected val table: String) : DatabaseRoute {

    override fun getTableName(): String = table

    override fun notifyChange(uri: Uri, notifyChange: (Uri) -> Unit ) {
        Preconditions.checkNotNull(uri, "URI cannot be null.")
        Preconditions.checkNotNull(notifyChange, "Notify Change function cannot be null.")

        notifyChange(uri)
    }
}
