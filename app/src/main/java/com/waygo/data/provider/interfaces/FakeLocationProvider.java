package com.waygo.data.provider.interfaces;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.utils.ObservableEx;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class FakeLocationProvider implements ILocationProvider {

    @NonNull
    @Override
    public Observable<GeoCoordinate> getLocation() {
        return ObservableEx.choose( Observable.interval(1, TimeUnit.SECONDS)
                         .map(__ -> GeoCoordinate.create(52.5388263, 13.3900338)), a -> a);
    }


}
