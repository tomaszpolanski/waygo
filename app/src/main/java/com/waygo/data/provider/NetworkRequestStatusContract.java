package com.waygo.data.provider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.waygo.data.base.contract.SerializedJsonContract;
import com.waygo.pojo.NetworkRequestStatus;

import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.lang.reflect.Type;

import rx.android.internal.Preconditions;

public class NetworkRequestStatusContract extends SerializedJsonContract<NetworkRequestStatus> {
    private static final String TABLE_NAME = "network_request_statuses";
    public static final Uri CONTENT_URI = Uri.parse("content://" + LufthansaContentProvider.PROVIDER_NAME + "/" + TABLE_NAME);
    private static final Type TYPE = new TypeToken<NetworkRequestStatus>() {}.getType();

    @NonNull
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @NonNull
    @Override
    protected String getCreateIdColumn() {
        return ID + " INTEGER PRIMARY KEY";
    }

    @NonNull
    @Override
    public ContentValues getContentValuesForItem(@NonNull NetworkRequestStatus item) {
        Preconditions.checkNotNull(item, "Network Request Status cannot be null.");

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, item.getUri().hashCode());
        contentValues.put(JSON, new Gson().toJson(item));
        return contentValues;
    }

    @NonNull
    @Override
    protected Type getType() {
        return TYPE;
    }
}
