package com.waygo.data;

import com.waygo.data.provider.UserSettingsContract;
import com.waygo.data.stores.GitHubRepositorySearchStore;
import com.waygo.data.stores.GitHubRepositoryStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.data.stores.UserSettingsStore;
import com.waygo.data.utils.DataLayerUtils;
import com.waygo.network.NetworkService;
import com.waygo.pojo.GitHubRepository;
import com.waygo.pojo.GitHubRepositorySearch;
import com.waygo.pojo.NetworkRequestStatus;
import com.waygo.pojo.UserSettings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.android.internal.Preconditions;

public class DataLayer extends com.waygo.data.DataLayerBase {
    private static final String TAG = DataLayer.class.getSimpleName();
    private final Context context;
    protected final UserSettingsStore userSettingsStore;

    public DataLayer(@NonNull Context context,
                     @NonNull UserSettingsStore userSettingsStore,
                     @NonNull NetworkRequestStatusStore networkRequestStatusStore,
                     @NonNull GitHubRepositoryStore gitHubRepositoryStore,
                     @NonNull GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        super(networkRequestStatusStore, gitHubRepositoryStore, gitHubRepositorySearchStore);

        Preconditions.checkNotNull(context, "Context cannot be null.");
        Preconditions.checkNotNull(userSettingsStore, "User Settings Store cannot be null.");

        this.context = context;
        this.userSettingsStore = userSettingsStore;
    }

    @NonNull
    public Observable<DataStreamNotification<GitHubRepositorySearch>> getGitHubRepositorySearch(@NonNull final String searchString) {
        Preconditions.checkNotNull(searchString, "Search string Store cannot be null.");

        final Uri uri = gitHubRepositorySearchStore.getUriForKey(searchString);
        final Observable<NetworkRequestStatus> networkRequestStatusObservable =
                networkRequestStatusStore.getStream(uri.toString().hashCode());
        final Observable<GitHubRepositorySearch> gitHubRepositorySearchObservable =
                gitHubRepositorySearchStore.getStream(searchString);
        return DataLayerUtils.createDataStreamNotificationObservable(
                        networkRequestStatusObservable, gitHubRepositorySearchObservable);
    }

    @NonNull
    public Observable<DataStreamNotification<GitHubRepositorySearch>> fetchAndGetGitHubRepositorySearch(@NonNull final String searchString) {
        Preconditions.checkNotNull(searchString, "Search string Store cannot be null.");

        final Observable<DataStreamNotification<GitHubRepositorySearch>> gitHubRepositoryStream =
                getGitHubRepositorySearch(searchString);
        fetchGitHubRepositorySearch(searchString);
        return gitHubRepositoryStream;
    }

    private void fetchGitHubRepositorySearch(@NonNull final String searchString) {
        Preconditions.checkNotNull(searchString, "Search string Store cannot be null.");

        Intent intent = new Intent(context, NetworkService.class);
        intent.putExtra("contentUriString", gitHubRepositorySearchStore.getContentUri().toString());
        intent.putExtra("searchString", searchString);
        context.startService(intent);
    }

    @NonNull
    public Observable<GitHubRepository> getGitHubRepository(@NonNull Integer repositoryId) {
        Preconditions.checkNotNull(repositoryId, "Repository Id cannot be null.");

        return gitHubRepositoryStore.getStream(repositoryId);
    }

    @NonNull
    public Observable<GitHubRepository> fetchAndGetGitHubRepository(@NonNull Integer repositoryId) {
        Preconditions.checkNotNull(repositoryId, "Repository Id cannot be null.");

        fetchGitHubRepository(repositoryId);
        return getGitHubRepository(repositoryId);
    }

    private void fetchGitHubRepository(@NonNull Integer repositoryId) {
        Intent intent = new Intent(context, NetworkService.class);
        intent.putExtra("contentUriString", gitHubRepositoryStore.getContentUri().toString());
        intent.putExtra("id", repositoryId);
        context.startService(intent);
    }

    @NonNull
    public Observable<UserSettings> getUserSettings() {
        return userSettingsStore.getStream(UserSettingsContract.DEFAULT_USER_ID);
    }

    public void setUserSettings(@NonNull UserSettings userSettings) {
        Preconditions.checkNotNull(userSettings, "User Settings cannot be null.");

        userSettingsStore.insertOrUpdate(userSettings);
    }

    public interface GetUserSettings {
        @NonNull
        Observable<UserSettings> call();
    }

    public interface SetUserSettings {
        void call(@NonNull UserSettings userSettings);
    }

    public interface GetGitHubRepository {
        @NonNull
        Observable<GitHubRepository> call(int repositoryId);
    }

    public interface FetchAndGetGitHubRepository extends GetGitHubRepository {

    }

    public interface GetGitHubRepositorySearch {
        @NonNull
        Observable<DataStreamNotification<GitHubRepositorySearch>> call(@NonNull String search);
    }
}
