package com.waygo.data.provider.interfaces


import com.waygo.data.model.GeoCoordinate
import com.waygo.data.model.fuel.Fuel
import com.waygo.utilskt.Result

import rx.Observable

public interface ILogBoxProvider {
    public fun getTankLevel(): Observable<Result<Fuel>>
    public fun getGeoPosition(): Observable<GeoCoordinate>
}
