package com.waygo.data.stores;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.waygo.data.base.store.ContentProviderStoreBase;
import com.waygo.data.provider.UserSettingsContract;
import com.waygo.pojo.UserSettings;

public class UserSettingsStore extends ContentProviderStoreBase<UserSettings, Integer> {
    private static final String TAG = UserSettingsStore.class.getSimpleName();

    private static final int DEFAULT_REPOSITORY_ID = 15491874;

    public UserSettingsStore(@NonNull ContentResolver contentResolver) {
        super(contentResolver, new UserSettingsContract());
        if (!hasUserSettings()) {
            insertOrUpdate(new UserSettings(DEFAULT_REPOSITORY_ID));
        }
    }

    @NonNull
    @Override
    protected Integer getIdFor(@NonNull UserSettings item) {
        return UserSettingsContract.DEFAULT_USER_ID;
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return UserSettingsContract.CONTENT_URI;
    }

    private boolean hasUserSettings() {
        return query(UserSettingsContract.DEFAULT_USER_ID) != null;
    }
}
