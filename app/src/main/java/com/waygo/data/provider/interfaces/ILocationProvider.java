package com.waygo.data.provider.interfaces;


import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;

import rx.Observable;

public interface ILocationProvider {

    @NonNull
    Observable<GeoCoordinate> getLocation();
}
