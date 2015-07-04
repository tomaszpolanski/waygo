package com.waygo.data.provider;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.waygo.R;
import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.model.butler.ButlerSayResponse;
import com.waygo.data.model.butler.ButlerShowResponse;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.result.Result;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public final class FakeButler implements IButler {

    @NonNull
    public final Resources mResources;
    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    public FakeButler(@NonNull final Resources resources,
                      @NonNull final ISchedulerProvider schedulerProvider) {
        mResources = resources;
        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public Observable<Result<ButlerResponse>> ask(@NonNull final String question) {
        return Observable.create(new Observable.OnSubscribe<Result<ButlerResponse>>() {
            @Override
            public void call(Subscriber<? super Result<ButlerResponse>> subscriber) {
                subscriber.onNext(getResponse(question));
            }
        }).debounce(3, TimeUnit.SECONDS, mSchedulerProvider.getTimeScheduler());
    }

    @NonNull
    private Result<ButlerResponse> getResponse(@NonNull final String question) {
        switch (question.toLowerCase()) {
            case "say what?!":
                return ButlerSayResponse.create("What!");
            case "where to go?":
                return getBitmap(R.drawable.__leak_canary_icon, mResources)
                        .flatMap(bitmap -> ButlerShowResponse.create("Please go here:", bitmap));
            default:
                return Result.failure("Sorry, I didn't get that");
        }
    }

    @NonNull
    private static Result<Bitmap> getBitmap(final int id,
                                            @NonNull final Resources resources) {
        return Result.asResult(BitmapFactory.decodeResource(resources, id));
    }


}
