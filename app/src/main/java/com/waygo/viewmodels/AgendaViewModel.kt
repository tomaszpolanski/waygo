package com.waygo.viewmodels

import com.waygo.data.DataLayer
import com.waygo.data.DataStreamNotification
import com.waygo.pojo.flightstatus.Flight
import android.util.Log

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

import rx.Observable
import rx.android.internal.Preconditions
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription

public class AgendaViewModel(private val mFetchAndGetFlight: DataLayer.FetchAndGetGetFlightStatus) : AbstractViewModel() {

    private val mFlightSubject = BehaviorSubject.create<Flight>()
    private val mCalendar: Calendar
    private val mDateFormat: SimpleDateFormat

    init {
        Preconditions.checkNotNull(mFetchAndGetFlight, "Fetch And Get Flight Status cannot be null.")

        mCalendar = Calendar.getInstance()
        mDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }

    override fun subscribeToDataStoreInternal(subscriptions: CompositeSubscription) {
        Log.d(TAG, "Flight a : ")
        subscriptions.add(getFlightObservable())
    }

    fun getFlightObservable() =
            Observable.interval(10, TimeUnit.SECONDS)
                    .flatMap<DataStreamNotification<Flight>>{mFetchAndGetFlight.call("LH542", today)}
                    .filter{it.isOnNext()}
                    .map{it.getValue()}
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{mFlightSubject.onNext(it)}

    private val today : String get() = mDateFormat.format(mCalendar.getTime())

    public fun getFlight(): Observable<Flight> {
        return mFlightSubject.asObservable()
    }

    companion object {

        private val TAG = javaClass<AgendaViewModel>().getSimpleName()
    }

}
