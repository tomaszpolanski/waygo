package com.waygo.data.provider

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.waygo.data.base.contract.SerializedJsonContract
import com.waygo.pojo.flightstatus.Flight

import android.content.ContentValues
import android.net.Uri

import java.lang.reflect.Type

import rx.android.internal.Preconditions

public class FlightStatusContract : SerializedJsonContract<Flight>() {

    override fun getTableName(): String =  TABLE_NAME

    override fun getCreateIdColumn(): String = SerializedJsonContract.ID + " VARCHAR(20)"

    override fun getType(): Type  = TYPE

    override fun getContentValuesForItem(item: Flight): ContentValues {
        Preconditions.checkNotNull(item, "Flight cannot be null.")

        val contentValues = ContentValues()
        contentValues.put(SerializedJsonContract.ID, item.getId())
        contentValues.put(SerializedJsonContract.JSON, Gson().toJson(item))
        return contentValues
    }



    companion object {
        private val TABLE_NAME = "flight_status"
        public val CONTENT_URI: Uri = Uri.parse("content://" + LufthansaContentProvider.PROVIDER_NAME + "/" + TABLE_NAME)
        private val TYPE = object : TypeToken<Flight>(){}.getType()
    }
}
