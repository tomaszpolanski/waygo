package com.waygo.data;

import com.waygo.data.stores.FlightStatusStore;
import com.waygo.data.stores.NetworkRequestStatusStore;
import com.waygo.data.utils.DataLayerUtils;
import com.waygo.network.NetworkService;
import com.waygo.pojo.NetworkRequestStatus;
import com.waygo.pojo.flightstatus.Flight;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.android.internal.Preconditions;

public class DataLayer extends com.waygo.data.DataLayerBase {

    private static final String TAG = DataLayer.class.getSimpleName();

    private final Context context;

    public DataLayer(@NonNull Context context,
                     @NonNull NetworkRequestStatusStore networkRequestStatusStore,
                     @NonNull FlightStatusStore flightStatusStore) {
        super(networkRequestStatusStore, flightStatusStore);

        Preconditions.checkNotNull(context, "Context cannot be null.");

        this.context = context;
    }

    @NonNull
    public Observable<DataStreamNotification<Flight>> getFlightStatus(
            @NonNull final String flightNumber,
            @NonNull final String date) {
        Preconditions.checkNotNull(flightNumber, "Flight Number cannot be null.");
        Preconditions.checkNotNull(date, "date cannot be null.");

        final String id = flightNumber + date.replace("-", "");
        final Uri uri = flightStatusStore.getUriForKey(id);
        final Observable<NetworkRequestStatus> networkRequestStatusObservable =
                networkRequestStatusStore.getStream(uri.toString().hashCode());
        final Observable<Flight> flightStatusObservable =
                flightStatusStore.getStream(id);
        return DataLayerUtils.createDataStreamNotificationObservable(
                networkRequestStatusObservable, flightStatusObservable);
    }

    @NonNull
    public Observable<DataStreamNotification<Flight>> fetchAndGetFlightStatus(
            @NonNull final String flightNumber,
            @NonNull final String date) {
        Preconditions.checkNotNull(flightNumber, "Flight Number cannot be null.");
        Preconditions.checkNotNull(date, "date cannot be null.");

        final Observable<DataStreamNotification<Flight>> flightStatusObservable =
                getFlightStatus(flightNumber, date);
        fetchFlightStatus(flightNumber, date);
        return flightStatusObservable.distinctUntilChanged();
    }

    private void fetchFlightStatus(@NonNull final String flightNumber,
                                   @NonNull final String date) {
        Preconditions.checkNotNull(flightNumber, "Flight Number cannot be null.");
        Preconditions.checkNotNull(date, "date cannot be null.");

        Intent intent = new Intent(context, NetworkService.class);
        intent.putExtra("contentUriString", flightStatusStore.getContentUri().toString());
        intent.putExtra("flightNumber", flightNumber);
        intent.putExtra("date", date);
        context.startService(intent);
    }


    public interface FetchAndGetGetFlightStatus extends GetFlightStatus {

    }

    public interface GetFlightStatus {

        @NonNull
        Observable<DataStreamNotification<Flight>> call(@NonNull String flightNumber,
                                                        @NonNull String date);
    }
}
