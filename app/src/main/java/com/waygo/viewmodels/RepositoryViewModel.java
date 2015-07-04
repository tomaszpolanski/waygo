package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.DataStreamNotification;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.pojo.flightstatus.Flight;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.Option;
import com.waygo.utils.result.Result;

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

public class RepositoryViewModel extends AbstractViewModel {

    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    private final DataLayer.GetFlightStatus mGetFlightStatus;

    private final DataLayer.FetchAndGetGetFlightStatus mFetchAndGetFlight;

    final private BehaviorSubject<Flight> mFlightSubject = BehaviorSubject.create();

    private final Observable<Fuel> mFuel;

    private final Calendar mCalendar;

    private final SimpleDateFormat mDateFormat;

    public RepositoryViewModel(@NonNull DataLayer.GetFlightStatus getFlightStatus,
                               @NonNull DataLayer.FetchAndGetGetFlightStatus fetchAndGetFlight,
                               @NonNull ILogBoxProvider logBoxProvider) {
        Preconditions.checkNotNull(getFlightStatus, "Get Flight Status cannot be null.");
        Preconditions.checkNotNull(fetchAndGetFlight,
                                   "Fetch And Get Flight Status cannot be null.");

        this.mGetFlightStatus = getFlightStatus;
        mFetchAndGetFlight = fetchAndGetFlight;
        mFuel = ObservableEx.choose(logBoxProvider.getTankLevel()
                                                  .map(Result::asOption), Option::id);
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription subscriptions) {
        subscriptions.add(
                Observable.interval(4, TimeUnit.SECONDS)
                          .take(999)
                          .map(Object::toString)
                          .map(index -> "LH" + index)
                          .flatMap(name -> mFetchAndGetFlight.call(name, getToday()))
                          .filter(DataStreamNotification::isOnNext)
                          .map(DataStreamNotification::getValue)
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(mFlightSubject::onNext,
                                     throwable -> Log.e(TAG, "Flight error: ",
                                                        throwable)));
    }

    @NonNull
    private String getToday() {
        return mDateFormat.format(mCalendar.getTime());
    }

    @NonNull
    public Observable<Flight> getFlight() {
        return mFlightSubject.asObservable();
    }

    @NonNull
    public Observable<Fuel> getFuel() {
        return mFuel;
    }

}
