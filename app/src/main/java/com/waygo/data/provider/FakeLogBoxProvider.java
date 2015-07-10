package com.waygo.data.provider;

import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.model.fuel.Electric;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.data.model.fuel.Premium;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utilskt.Option;
import com.waygo.utilskt.Result;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public final class FakeLogBoxProvider implements ILogBoxProvider {

    private final ISchedulerProvider mSchedulerProvider;

    public FakeLogBoxProvider(final @NonNull ISchedulerProvider schedulerProvider) {

        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public Observable<Result<Fuel>> getTankLevel() {
        return generatePremium(mSchedulerProvider)
                .mergeWith(generateElectric(mSchedulerProvider));
    }

    @NonNull
    @Override
    public Observable<GeoCoordinate> getGeoPosition() {
        return ObservableEx.chooseKt(ObservableEx.repeatTimer(GeoCoordinate.create(52.4388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler()),
                new Func1<Option<GeoCoordinate>, Option<GeoCoordinate>>() {
                    @Override
                    public Option<GeoCoordinate> call(Option<GeoCoordinate> op) {
                        return op;
                    }
                })
                .share();
    }

    @NonNull
    private static Observable<Result<Fuel>> generatePremium(final @NonNull ISchedulerProvider schedulerProvider) {
        return generateValue(new Func1<Float, Result<Fuel>>() {
            @Override
            public Result<Fuel> call(Float f) {
                return Premium.Companion.create(f);
            }
        }, schedulerProvider);
    }

    @NonNull
    private static Observable<Result<Fuel>> generateElectric(final @NonNull ISchedulerProvider schedulerProvider) {
        return generateValue(new Func1<Float, Result<Fuel>>() {
            @Override
            public Result<Fuel> call(Float f) {
                return Electric.Companion.create(f);
            }
        }, schedulerProvider);
    }

    @NonNull
    private static Observable<Result<Fuel>> generateValue( final @NonNull Func1<Float, Result<Fuel>> create,
                                                           final @NonNull ISchedulerProvider schedulerProvider) {
        return Observable.timer(1, 1, TimeUnit.SECONDS, schedulerProvider.getTimeScheduler())
                .map(s -> 0.7f - (float) s / (float) 100)
                .map(create)
                .share();
    }
}
