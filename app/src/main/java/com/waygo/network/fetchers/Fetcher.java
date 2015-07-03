package com.waygo.network.fetchers;

import android.content.Intent;
import android.net.Uri;

public interface Fetcher {
    void fetch(Intent intent);
    Uri getContentUri();
}
