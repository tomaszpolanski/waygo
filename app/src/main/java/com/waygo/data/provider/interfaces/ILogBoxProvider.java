package com.waygo.data.provider.interfaces;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.utils.result.Result;

import rx.Observable;

public interface ILogBoxProvider {

    @NonNull
    Observable<Result<Fuel>>  getTankLevel();

    @NonNull
    Observable<GeoCoordinate> getGeoPosition();
}
