package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.network.LufthansaAccountService;
import com.waygo.network.ServiceGenerator;
import com.waygo.pojo.GitHubRepository;
import com.waygo.pojo.UserSettings;

import android.support.annotation.NonNull;
import android.util.Log;

import rx.Observable;
import rx.android.internal.Preconditions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class RepositoryViewModel extends AbstractViewModel {
    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    private final DataLayer.GetUserSettings getUserSettings;
    private final DataLayer.FetchAndGetGitHubRepository fetchAndGetGitHubRepository;

    final private BehaviorSubject<GitHubRepository> repository = BehaviorSubject.create();

    public RepositoryViewModel(@NonNull DataLayer.GetUserSettings getUserSettings,
                               @NonNull DataLayer.FetchAndGetGitHubRepository fetchAndGetGitHubRepository) {
        Preconditions.checkNotNull(getUserSettings, "Gey User Settings cannot be null.");
        Preconditions.checkNotNull(fetchAndGetGitHubRepository,
                                   "Fetch And Get GitHub Repository cannot be null.");

        this.getUserSettings = getUserSettings;
        this.fetchAndGetGitHubRepository = fetchAndGetGitHubRepository;
        Log.v(TAG, "RepositoryViewModel");
    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription compositeSubscription) {
        compositeSubscription.add(
                getUserSettings.call()
                        .map(UserSettings::getSelectedRepositoryId)
                        .switchMap(fetchAndGetGitHubRepository::call)
                        .subscribe(repository)
        );

        LufthansaAccountService service = ServiceGenerator
                .createService(LufthansaAccountService.class,
                               LufthansaAccountService.BASE_URL);

        compositeSubscription.add(service.getAccessToken("mphx5vxg66a653d7ct4bzdst", "q7wHAfbKhj", "client_credentials")
               .doOnNext(accessToken -> Log.d(TAG, "Token: " + accessToken))
               .map(accessToken -> ServiceGenerator
                       .createService(LufthansaAccountService.class,
                                      LufthansaAccountService.BASE_URL,
                                      accessToken))
               .flatMap(lufthansaAccountService -> lufthansaAccountService
                       .getFlightStatus("LH400", "2015-07-03"))
               .map(flightStatusResource -> flightStatusResource.getmFlightStatusResource().getFlights().getFlight())
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(flights -> Log.d(TAG, "Flights: " + flights),
                          throwable -> Log.e(TAG, "Token error: ", throwable)));
    }

    @NonNull
    public Observable<GitHubRepository> getRepository() {
        return repository.asObservable();
    }
}
