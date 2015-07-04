package com.waygo.data.provider;

import com.waygo.data.base.contract.SerializedJsonContract;
import com.waygo.data.base.route.DatabaseRouteBase;

import android.net.Uri;
import android.support.annotation.NonNull;

public class NetworkRequestStatusSingleRoute extends DatabaseRouteBase {
    private static final String MULTIPLE_MIME_TYPE =
            "vnd.android.cursor.dir/vnd.waygo.android.networkrequeststatus";

    public NetworkRequestStatusSingleRoute(@NonNull final String tableName) {
        super(tableName);
    }

    @NonNull
    @Override
    public String getMimeType() {
        return MULTIPLE_MIME_TYPE;
    }

    @NonNull
    @Override
    public String getWhere(Uri uri) {
        int uriHash = Integer.parseInt(uri.getLastPathSegment());
        return NetworkRequestStatusContract.ID + " = " + uriHash;
    }

    @NonNull
    public String getDefaultSortOrder() {
        return SerializedJsonContract.ID + " ASC";
    }

    @NonNull
    @Override
    public String getPath() {
        return tableName + "/*";
    }
}
