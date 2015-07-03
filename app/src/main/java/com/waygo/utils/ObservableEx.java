package com.waygo.utils;


import com.waygo.utils.option.Option;
import com.waygo.utils.result.Result;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public final class ObservableEx<T> extends Observable<T> {

    protected ObservableEx(OnSubscribe<T> f) {
        super(f);
    }


    public static <T> Observable<T> timer(final T value, final long seconds, final Scheduler scheduler) {
        return Observable.timer(seconds, TimeUnit.SECONDS, scheduler)
                         .map(__ -> value);
    }

    public static Observable<Integer> randTimer(final int min, final int max, final long seconds, final Scheduler scheduler) {
        return Observable.timer(seconds, TimeUnit.SECONDS, scheduler)
                .map(__ -> Rand.randInt(min, max));
    }

    public static <T> Observable<T> late(final Observable<T> observable, long delay, TimeUnit unit) {
        return observable.flatMap(val -> Observable.timer(delay, unit).map(__ -> val));
    }

    public static <T> Observable<T> repeatTimer(final T value, final long startSeconds, final long seconds, final Scheduler scheduler) {
        return Observable.timer(startSeconds, seconds, TimeUnit.SECONDS, scheduler)
                         .map(__ -> value);
    }

    public static <T> Observable<T> filterOption(Observable<Option<T>> o) {
        return o.filter(option -> option != Option.NONE)
                .map(Option::getUnsafe);
    }

    public static <T> Observable<T> filterResult(Observable<Result<T>> r) {
        return r.filter(Result::isSuccess)
                .map(Result::getUnsafe);
    }

    public static <T, R> Observable<R> choose(final Observable<T> o, final Func1<T, Option<R>> selector) {
        return o.map(selector)
                .filter(option -> option != Option.NONE)
                .map(Option::getUnsafe);
    }

    public static Observable<String> defineError(final Observable<? extends Result> error) {
        return error.filter(result -> !result.isSuccess())
                    .map(Result::getMessage);
    }


}
