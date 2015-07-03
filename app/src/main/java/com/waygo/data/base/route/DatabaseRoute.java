package com.waygo.data.base.route;

import android.net.Uri;
import android.support.annotation.NonNull;

import rx.functions.Action1;

public interface DatabaseRoute {
    @NonNull String getPath();
    @NonNull String getTableName();
    @NonNull String getDefaultSortOrder();
    @NonNull String getWhere(Uri uri);
    @NonNull String getMimeType();
    void notifyChange(@NonNull Uri uri, @NonNull Action1<Uri> notifyChange);
}
