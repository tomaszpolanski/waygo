package com.waygo.data.provider;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.waygo.data.base.contract.SerializedJsonContract;
import com.waygo.data.base.route.DatabaseRouteBase;

import rx.android.internal.Preconditions;

public class GitHubRepositorySearchSingleRoute extends DatabaseRouteBase {
    private static final String SINGLE_MIME_TYPE =
            "vnd.android.cursor.item/vnd.tehmou.android.rxbookapp.repositorysearch";

    public GitHubRepositorySearchSingleRoute(@NonNull final String tableName) {
        super(tableName);
    }

    @NonNull
    public String getDefaultSortOrder() {
        return SerializedJsonContract.ID + " ASC";
    }

    @NonNull
    @Override
    public String getMimeType() {
        return SINGLE_MIME_TYPE;
    }

    @NonNull
    public String getWhere(@NonNull Uri uri) {
        Preconditions.checkNotNull(uri, "URI cannot be null.");

        return SerializedJsonContract.ID + " = '" + uri.getLastPathSegment() + "'";
    }

    @NonNull
    @Override
    public String getPath() {
        return tableName + "/*";
    }
}
