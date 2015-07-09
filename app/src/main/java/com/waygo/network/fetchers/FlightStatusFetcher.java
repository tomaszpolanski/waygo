package com.waygo.network.fetchers;

import com.waygo.data.stores.FlightStatusStore;
import com.waygo.network.LufthansaAccountService;
import com.waygo.pojo.NetworkRequestStatus;
import com.waygo.pojo.flightstatus.BaseLufthansaResponse;
import com.waygo.pojo.flightstatus.FlightStatusContainer;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import rx.Observable;
import rx.Subscription;
import rx.android.internal.Preconditions;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FlightStatusFetcher extends FetcherBase {

    private static final String TAG = FlightStatusFetcher.class.getSimpleName();

    @NonNull
    private final FlightStatusStore flightStatusStore;

    public FlightStatusFetcher(@NonNull LufthansaAccountService networkApi,
                               @NonNull Action1<NetworkRequestStatus> updateNetworkRequestStatus,
                               @NonNull FlightStatusStore flightStatusStore) {
        super(networkApi, updateNetworkRequestStatus);

        Preconditions.checkNotNull(flightStatusStore, "Flight Status Store cannot be null.");

        this.flightStatusStore = flightStatusStore;
    }

    @Override
    public void fetch(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent, "Fetch Intent cannot be null.");

        final String flightNumber = intent.getStringExtra("flightNumber");
        final String date = intent.getStringExtra("date");
        if (!TextUtils.isEmpty(flightNumber) && !TextUtils.isEmpty(date)) {
            fetchFlightStatus(flightNumber, date);
        } else {
            Log.e(TAG, "No valid details provided in the intent extras");
        }
    }

    private void fetchFlightStatus(String flightNumber, String date) {
        Log.d(TAG, "fetchFlightStatus(" + flightNumber + ", " + date + ")");
        final String id = flightNumber + date.replace("-", "");
        if (requestMap.containsKey(id) &&
            !requestMap.get(id).isUnsubscribed()) {
            Log.d(TAG, "Found an ongoing request for repository " + id);
            return;
        }
        final String uri = flightStatusStore.getUriForKey(id).toString();
        Subscription subscription = networkApi.getFlightStatus(flightNumber, date)
                                              .subscribeOn(Schedulers.computation())
                                              .doOnError(doOnError(uri))
                                              .doOnCompleted(() -> completeRequest(uri))
                                              .groupBy(BaseLufthansaResponse::isError)
                                              .flatMap(error -> error.getKey()
                                                      ? getError(error)
                                                      : error)
                                              .map(flightStatusContainer -> flightStatusContainer
                                                      .getFlightStatusResource()
                                                      .getFlights()
                                                      .getFlight())
                                              .subscribe(flightStatusStore::put,
                                                         e -> Log.e(TAG,
                                                                    "Error fetching flight "
                                                                    + id, e));
        requestMap.put(id, subscription);
        startRequest(uri);
    }

    @NonNull
    private Observable<FlightStatusContainer> getError(Observable<FlightStatusContainer> error) {
        return error.flatMap(
                flightStatusContainer -> Observable
                        .error(new RuntimeException(
                                flightStatusContainer
                                        .getProcessingErrors()
                                        .getProcessingError()
                                        .getDescription())));
    }

    @NonNull
    @Override
    public Uri getContentUri() {
        return flightStatusStore.getContentUri();
    }
}
