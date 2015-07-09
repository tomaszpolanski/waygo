package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.DataStreamNotification;
import com.waygo.pojo.flightstatus.Flight;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.internal.Preconditions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class AgendaViewModel extends AbstractViewModel {

    private static final String TAG = AgendaViewModel.class.getSimpleName();

    private final DataLayer.FetchAndGetGetFlightStatus mFetchAndGetFlight;

    private final BehaviorSubject<Flight> mFlightSubject = BehaviorSubject.create();

    private final Calendar mCalendar;

    private final SimpleDateFormat mDateFormat;

    public AgendaViewModel(@NonNull final DataLayer.FetchAndGetGetFlightStatus fetchAndGetFlight) {
        Preconditions.checkNotNull(fetchAndGetFlight,
                                   "Fetch And Get Flight Status cannot be null.");

        mFetchAndGetFlight = fetchAndGetFlight;

        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription subscriptions) {
        Log.d(TAG, "Flight a : ");
        subscriptions.add(
                Observable.interval(10, TimeUnit.SECONDS)
                          .flatMap(__ -> mFetchAndGetFlight.call("LH542", getToday()))
                          .filter(DataStreamNotification::isOnNext)
                          .map(DataStreamNotification::getValue)
                          .distinctUntilChanged()
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(mFlightSubject::onNext,
                                     throwable -> Log.e(TAG, "Flight error: ", throwable)));
    }

    @NonNull
    private String getToday() {
        return mDateFormat.format(mCalendar.getTime());
    }

    @NonNull
    public Observable<Flight> getFlight() {
        return mFlightSubject.asObservable();
    }

}
