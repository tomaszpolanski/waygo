package com.waygo.data.provider.interfaces;

import android.support.annotation.NonNull;

import rx.Scheduler;

public interface ISchedulerProvider {
    @NonNull
    Scheduler getTimeScheduler();
    @NonNull
    Scheduler getUiScheduler();
}
