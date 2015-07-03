package com.waygo.data.provider.interfaces;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public interface ISchedulerProvider {
    @NonNull
    Scheduler getTimeScheduler();
    @NonNull
    Scheduler getUiScheduler();
}
