package com.waygo.viewmodels;

import android.support.annotation.NonNull;

import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.result.Result;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class ChatViewModel extends AbstractViewModel {

    private static final String TAG = ChatViewModel.class.getSimpleName();


    private final PublishSubject<String> mQuestion = PublishSubject.create();

    private final PublishSubject<String> mQuestionResponse = PublishSubject.create();

    @NonNull
    private final IButler mButler;


    public ChatViewModel( @NonNull final IButler buttler) {
        mButler = buttler;

    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription subscriptions) {

        final Observable<Result<ButlerResponse>> butlerResponse =
                mQuestion.switchMap(mButler::ask)
                        .share();

        final Observable<String> validResponse = ObservableEx.choose(butlerResponse, Result::asOption)
                .map(ButlerResponse::getMessage);

        subscriptions.add(validResponse.subscribe(mQuestionResponse));
        subscriptions.add(ObservableEx.defineError(butlerResponse).subscribe(mQuestionResponse));
    }

    @NonNull
    public Observable<String> getResponse() {
        return mQuestionResponse;
    }


    public void askButler(@NonNull final String question) {
        mQuestion.onNext(question);
    }
}
