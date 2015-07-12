package com.waygo.data.provider


import com.waygo.data.model.GeoCoordinate
import com.waygo.data.provider.interfaces.ILocationProvider
import com.waygo.data.provider.interfaces.ISchedulerProvider
import com.waygo.utils.ObservableEx
import com.waygo.utilskt.choose
import rx.Observable

public class FakeLocationProvider(private val mSchedulerProvider: ISchedulerProvider) : ILocationProvider {

    override fun getLocation(): Observable<GeoCoordinate> =
            ObservableEx.repeatTimer(GeoCoordinate.create(52.5388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler())
                    .choose { it }
                    .share()


}
