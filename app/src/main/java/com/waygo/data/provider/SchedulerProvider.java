package com.waygo.data.provider;


import android.support.annotation.NonNull;

import com.waygo.data.provider.interfaces.ISchedulerProvider;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class SchedulerProvider implements ISchedulerProvider {
    @NonNull
    @Override
    public Scheduler getTimeScheduler() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler getUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
