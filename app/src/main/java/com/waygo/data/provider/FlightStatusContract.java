package com.waygo.data.provider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.waygo.data.base.contract.SerializedJsonContract;
import com.waygo.pojo.flightstatus.Flight;

import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import rx.android.internal.Preconditions;

public class FlightStatusContract extends SerializedJsonContract<Flight> {
    private static final String TABLE_NAME = "flight_status";
    public static final Uri CONTENT_URI = Uri.parse("content://" + LufthansaContentProvider.PROVIDER_NAME + "/" + TABLE_NAME);
    private static final Type TYPE = new TypeToken<Flight>() {}.getType();

    @NonNull
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @NonNull
    @Override
    protected String getCreateIdColumn() {
        return ID + " VARCHAR(20)";
    }

    @NonNull
    @Override
    public ContentValues getContentValuesForItem(@NonNull Flight item) {
        Preconditions.checkNotNull(item, "Flight cannot be null.");

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, item.getId());
        contentValues.put(JSON, new Gson().toJson(item));
        return contentValues;
    }

    @NonNull
    @Override
    protected Type getType() {
        return TYPE;
    }
}
