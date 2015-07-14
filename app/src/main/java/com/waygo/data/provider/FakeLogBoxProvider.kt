package com.waygo.data.provider


import com.waygo.data.model.GeoCoordinate
import com.waygo.data.model.fuel.Electric
import com.waygo.data.model.fuel.Fuel
import com.waygo.data.model.fuel.Premium
import com.waygo.data.provider.interfaces.ILogBoxProvider
import com.waygo.data.provider.interfaces.ISchedulerProvider
import com.waygo.utils.ObservableEx
import com.waygo.utilskt.Option
import com.waygo.utilskt.Result
import com.waygo.utilskt.choose

import java.util.concurrent.TimeUnit

import rx.Observable
import rx.functions.Func1

public class FakeLogBoxProvider(private val mSchedulerProvider: ISchedulerProvider) : ILogBoxProvider {

    override fun getTankLevel(): Observable<Result<Fuel>> {
        return generatePremium(mSchedulerProvider).mergeWith(generateElectric(mSchedulerProvider))
    }

    override fun getGeoPosition(): Observable<GeoCoordinate> {
        return ObservableEx.repeatTimer(GeoCoordinate.create(52.4388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler())
                .choose { it }
                .share()
    }

    private fun generatePremium(schedulerProvider: ISchedulerProvider): Observable<Result<Fuel>> {
        return generateValue({ Premium.create(it) }, schedulerProvider)
    }

    private fun generateElectric(schedulerProvider: ISchedulerProvider): Observable<Result<Fuel>> {
        return generateValue( { Electric.create(it) }, schedulerProvider)
    }

    private fun generateValue(create: (Float) -> Result<Fuel>, schedulerProvider: ISchedulerProvider): Observable<Result<Fuel>> {
        return Observable.timer(1, 1, TimeUnit.SECONDS, schedulerProvider.getTimeScheduler())
                .map{0.7f -  it /  100 }
                .map(create).
                share()
    }
}
