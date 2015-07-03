package com.waygo.data.provider;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.provider.interfaces.ILocationProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.Option;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class FakeLocationProvider implements ILocationProvider {

    @NonNull
    @Override
    public Observable<GeoCoordinate> getLocation() {
        return ObservableEx.choose( Observable.interval(1, TimeUnit.SECONDS)
                         .map(__ -> GeoCoordinate.create(52.5388263, 13.3900338)), Option::id);
    }


}
