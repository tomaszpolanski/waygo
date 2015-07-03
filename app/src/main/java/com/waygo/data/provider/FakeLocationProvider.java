package com.waygo.data.provider;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.provider.interfaces.ILocationProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.Option;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class FakeLocationProvider implements ILocationProvider {

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    public FakeLocationProvider(final @NonNull ISchedulerProvider schedulerProvider) {

        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public Observable<GeoCoordinate> getLocation() {
        return ObservableEx.choose( ObservableEx.repeatTimer(GeoCoordinate.create(52.5388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler()),
                Option::id)
                .share();
    }


}
