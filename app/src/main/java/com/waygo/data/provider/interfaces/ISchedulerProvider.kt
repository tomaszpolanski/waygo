package com.waygo.data.provider.interfaces


import rx.Scheduler

public interface ISchedulerProvider {
    public fun getTimeScheduler(): Scheduler
    public fun getUiScheduler(): Scheduler
}
