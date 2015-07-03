package com.waygo.data.provider;

import android.support.annotation.NonNull;

import com.waygo.data.model.GeoCoordinate;
import com.waygo.data.model.fuel.Electric;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.data.model.fuel.Premium;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.option.Option;
import com.waygo.utils.result.Result;

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
        return ObservableEx.choose(ObservableEx.repeatTimer(GeoCoordinate.create(52.4388263, 13.3900338), 1, 1, mSchedulerProvider.getTimeScheduler()),
                Option::id)
                .share();
    }

    @NonNull
    private static Observable<Result<Fuel>> generatePremium(final @NonNull ISchedulerProvider schedulerProvider) {
        return generateValue(Premium::create, schedulerProvider);
    }

    @NonNull
    private static Observable<Result<Fuel>> generateElectric(final @NonNull ISchedulerProvider schedulerProvider) {
        return generateValue(Electric::create, schedulerProvider);
    }

    @NonNull
    private static Observable<Result<Fuel>> generateValue( final @NonNull Func1<Float, Result<Fuel>> create,
                                                           final @NonNull ISchedulerProvider schedulerProvider) {
        return Observable.timer(1, 1, TimeUnit.SECONDS, schedulerProvider.getTimeScheduler())
                .map(s -> 0.1f - (float) s / (float) 100)
                .map(create)
                .share();
    }
}
