package com.waygo.data.provider

import com.waygo.data.provider.interfaces.ISchedulerProvider

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

public class SchedulerProvider : ISchedulerProvider {
    override fun getTimeScheduler(): Scheduler = Schedulers.io()
    override fun getUiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
