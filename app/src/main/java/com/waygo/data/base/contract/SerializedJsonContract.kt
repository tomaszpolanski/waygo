package com.waygo.data.base.contract

import android.database.Cursor

import com.google.gson.Gson

import java.lang.reflect.Type

import rx.android.internal.Preconditions

public abstract class SerializedJsonContract<T> : DatabaseContract<T> {

    override fun getCreateTable(): String =
            " CREATE TABLE ${getTableName()}( ${getCreateIdColumn()}, $JSON TEXT NOT NULL);"

    override fun getDropTable(): String = "DROP TABLE IF EXISTS " + getTableName()

    override fun getProjection(): Array<String> = PROJECTION

    abstract override fun getTableName(): String

    protected abstract fun getCreateIdColumn(): String

    protected abstract fun getType(): Type

    override fun read(cursor: Cursor): T {
        Preconditions.checkNotNull(cursor, "Cursor cannot be null.")

        if (cursor.moveToFirst()) {
            val json = cursor.getString(cursor.getColumnIndex(SerializedJsonContract.JSON))
            return Gson().fromJson<T>(json, getType())
        }
        return null
    }

    companion object {
        public val ID: String = "id"
        public val JSON: String = "json"
        private val PROJECTION = arrayOf(ID, JSON)
    }
}
