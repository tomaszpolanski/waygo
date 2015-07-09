package com.waygo.data.provider;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.provider.interfaces.ILocationProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.OptionJ;
import com.waygo.utilskt.Option;

import rx.Observable;
import rx.functions.Func1;

public class FakeLocationProvider implements ILocationProvider {

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    public FakeLocationProvider(final @NonNull ISchedulerProvider schedulerProvider) {

        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public Observable<GeoCoordinate> getLocation() {
        return ObservableEx.chooseKt(ObservableEx.repeatTimer(GeoCoordinate.create(52.5388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler()),
                new Func1<Option<GeoCoordinate>, Option<GeoCoordinate>>() {
                    @Override
                    public Option<GeoCoordinate> call(Option<GeoCoordinate> op) {
                        return op;
                    }
                })
                .share();
    }


}
