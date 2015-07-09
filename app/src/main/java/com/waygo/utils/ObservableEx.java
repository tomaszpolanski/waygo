package com.waygo.utils;


import android.support.annotation.NonNull;

import com.waygo.utils.option.OptionJ;
import com.waygo.utils.result.ResultJ;
import com.waygo.utilskt.Option;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
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

    public static <T> Observable<T> filterOption(Observable<OptionJ<T>> o) {
        return o.filter(option -> option != OptionJ.NONE_J)
                .map(OptionJ::getUnsafe);
    }

    public static <T> Observable<T> filterResult(Observable<ResultJ<T>> r) {
        return r.filter(ResultJ::isSuccess)
                .map(ResultJ::getUnsafe);
    }

    public static <T, R> Observable<R> choose(final Observable<T> o, final Func1<T, OptionJ<R>> selector) {
        return o.map(selector)
                .filter(option -> option != OptionJ.NONE_J)
                .map(OptionJ::getUnsafe);
    }

    public static <T, R> Observable<R> chooseKt(final Observable<T> o, final Func1<T, Option<R>> selector) {
        return o.map(selector)
                .filter(new Func1<Option<R>, Boolean>() {
                    @Override
                    public Boolean call(Option<R> option) {
                        return option.getIsSome();
                    }
                })
                .map(new Func1<Option<R>, R>() {
                    @Override
                    public R call(Option<R> oo) {
                        return oo.getUnsafe();
                    }
                });
    }

    public static Observable<String> defineError(final Observable<? extends ResultJ> error) {
        return error.filter(result -> !result.isSuccess())
                    .map(ResultJ::getMessage);
    }

    public static <T> Observable<T> delayEach(@NonNull final List<T> elements, int seconds, @NonNull final Scheduler scheduler ) {
        return Observable.create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                for (T ele : elements) {
                    try {
                        Thread.sleep(seconds * 1000);
                    } catch (Exception e) {

                    }
                    subscriber.onNext(ele);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(scheduler);
    }


}
