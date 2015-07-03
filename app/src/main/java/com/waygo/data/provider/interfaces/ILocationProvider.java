package com.waygo.data.provider.interfaces;


import com.waygo.data.model.GeoCoordinate;

import rx.Observable;

public interface ILocationProvider {

    Observable<GeoCoordinate> getLocation();
}
