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
        return ObservableEx.randTimer(0, 100, 1, schedulerProvider.getTimeScheduler())
                .<Result<Fuel>>map(val -> Premium.create( val / 100 ));
    }

    @NonNull
    private static Observable<Result<Fuel>> generateElectric(final @NonNull ISchedulerProvider schedulerProvider) {
        return ObservableEx.randTimer(0, 100, 1, schedulerProvider.getTimeScheduler())
                .<Result<Fuel>>map(val -> Electric.create( val / 100 ));
    }
}
