package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.DataStreamNotification;
import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.pojo.flightstatus.Flight;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.OptionJ;
import com.waygo.utils.result.ResultJ;

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
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class RepositoryViewModel extends AbstractViewModel {

    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    private final DataLayer.GetFlightStatus mGetFlightStatus;

    private final DataLayer.FetchAndGetGetFlightStatus mFetchAndGetFlight;

    private final BehaviorSubject<Flight> mFlightSubject = BehaviorSubject.create();

    private final PublishSubject<String> mQuestion = PublishSubject.create();

    private final PublishSubject<String> mQuestionResponse = PublishSubject.create();


    @NonNull
    private final IButler mButtler;

    private final Calendar mCalendar;

    private final SimpleDateFormat mDateFormat;

    public RepositoryViewModel(@NonNull final DataLayer.GetFlightStatus getFlightStatus,
                               @NonNull final DataLayer.FetchAndGetGetFlightStatus fetchAndGetFlight,
                               @NonNull final ILogBoxProvider logBoxProvider,
                               @NonNull final IButler buttler) {
        mButtler = buttler;
        Preconditions.checkNotNull(getFlightStatus, "Get Flight Status cannot be null.");
        Preconditions.checkNotNull(fetchAndGetFlight,
                                   "Fetch And Get Flight Status cannot be null.");

        this.mGetFlightStatus = getFlightStatus;
        mFetchAndGetFlight = fetchAndGetFlight;

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
        final Observable<ResultJ<ButlerResponse>> butlerResponse =
                mQuestion.switchMap(mButtler::ask)
                .share();

        final Observable<String> validResponse = ObservableEx.choose(butlerResponse, ResultJ::asOption)
                .map(ButlerResponse::getMessage);

        subscriptions.add(validResponse.subscribe(mQuestionResponse));
        subscriptions.add(ObservableEx.defineError(butlerResponse).subscribe(mQuestionResponse));
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
    public Observable<String> getResponse() {
        return mQuestionResponse;
    }


    public void askButler(@NonNull final String question) {
        mQuestion.onNext(question);
    }
}
