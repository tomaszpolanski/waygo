package com.waygo.data.provider;

import android.support.annotation.NonNull;

import com.waygo.data.model.fuel.Electric;
import com.waygo.data.model.fuel.Fuel;
import com.waygo.data.model.fuel.Premium;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.result.Result;

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
        return ObservableEx.timer(1.0f, 1, schedulerProvider.getTimeScheduler())
                .reduce((f, s) -> f >= 0 ? f - 0.01f : 0.0f)
                .<Result<Fuel>>map(create);
    }
}
