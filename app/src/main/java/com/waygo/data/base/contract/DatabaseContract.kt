package com.waygo.data.base.contract

import android.content.ContentValues
import android.database.Cursor

public interface DatabaseContract<T> {
    public fun getCreateTable(): String
    public fun getDropTable(): String
    public fun getTableName(): String
    public fun read(cursor: Cursor): T
    public fun getContentValuesForItem(item: T): ContentValues
    public fun getProjection(): Array<String>
}
