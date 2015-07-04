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

    public static final String TOILETS = "Waygo, where are the toilets?";
    public static final String HUNGRY = "Waygo, I'm Hungry.";
    public static final String CHEAPGOOD = "Yeah, That would be great something cheap and good.";
    public static final String MAP = "Yeah, perfect how do I get there?";
    public static final String CHEERS = "Cheers Waygo.";

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
        }).debounce(1, TimeUnit.SECONDS, mSchedulerProvider.getTimeScheduler());
    }

    @NonNull
    private Result<ButlerResponse> getResponse(@NonNull final String question) {
        switch (question) {
            case HUNGRY:
                return ButlerSayResponse.create("Hi, Hungry I thought your name was Jenny, Ha Ha :P So, Vegan like last time?");
            case TOILETS:
                return ButlerSayResponse.create("Next one is twenty metres on the left. ");
            case CHEAPGOOD:
                return ButlerSayResponse.create("Ok, There is \"&lt;a href=\"http://www.omnomnom.com\">Natural Beans&lt;/a>\" which is mid priced with four stars on foursquare? ");
            case MAP:
                return getBitmap(R.drawable.map, mResources)
                        .flatMap(bitmap -> ButlerShowResponse.create("Here's the map.", bitmap));
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
