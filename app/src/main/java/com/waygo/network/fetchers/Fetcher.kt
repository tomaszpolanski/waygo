package com.waygo.network.fetchers

import android.content.Intent
import android.net.Uri

public interface Fetcher {
    public fun fetch(intent: Intent)
    public fun getContentUri(): Uri
}
