package com.waygo.data.provider

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.waygo.data.base.contract.SerializedJsonContract
import com.waygo.pojo.NetworkRequestStatus

import android.content.ContentValues
import android.net.Uri

import java.lang.reflect.Type

import rx.android.internal.Preconditions

public class NetworkRequestStatusContract : SerializedJsonContract<NetworkRequestStatus>() {

    override fun getTableName(): String = TABLE_NAME
    override fun getCreateIdColumn(): String = SerializedJsonContract.ID + " INTEGER PRIMARY KEY"
    override fun getType(): Type = TYPE

    override fun getContentValuesForItem(item: NetworkRequestStatus): ContentValues {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.")

        val contentValues = ContentValues()
        contentValues.put(SerializedJsonContract.ID, item.uri!!.hashCode())
        contentValues.put(SerializedJsonContract.JSON, Gson().toJson(item))
        return contentValues
    }



    companion object {
        private val TABLE_NAME = "network_request_statuses"
        public val CONTENT_URI: Uri = Uri.parse("content://${LufthansaContentProvider.PROVIDER_NAME}/${TABLE_NAME}")
        private val TYPE = object : TypeToken<NetworkRequestStatus>(){}.getType()
    }
}
