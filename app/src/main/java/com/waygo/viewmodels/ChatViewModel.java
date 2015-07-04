package com.waygo.viewmodels;

import android.support.annotation.NonNull;
import android.util.Log;

import com.waygo.data.model.butler.ButlerResponse;
import com.waygo.data.provider.FakeButler;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.Linq;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class ChatViewModel extends AbstractViewModel {

    private static final String TAG = ChatViewModel.class.getSimpleName();


    private final PublishSubject<String> mQuestion = PublishSubject.create();

    private final PublishSubject<String> mQuestionResponse = PublishSubject.create();

    @NonNull
    private final IButler mButler;
    @NonNull
    private final ISchedulerProvider mSchedulerProvider;
    private Linq<String> mQuestionsList = getQuestions();

    public ChatViewModel( @NonNull final IButler butler,
                          @NonNull final ISchedulerProvider schedulerProvider) {
        mButler = butler;

        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    private static Linq<String> getQuestions() {
        final Linq<String> list = new Linq<>();
        list.add(FakeButler.TOILETS);
        list.add(FakeButler.CHEERS);
        list.add(FakeButler.HUNGRY);
        list.add(FakeButler.CHEAPGOOD);
        list.add(FakeButler.MAP);
        return list;
    }

    private String getQuestion() {
        final String question = mQuestionsList.first().orDefault(() -> "");
        mQuestionsList = mQuestionsList.skip(1);
        return question;
    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription subscriptions) {
        final Observable<String> questions =  Observable.interval(10, TimeUnit.SECONDS, mSchedulerProvider.getTimeScheduler())
                .map(__ -> getQuestion())
                .filter(a -> !a.isEmpty())
                .share();

        final Observable<String> answers = questions.switchMap(s -> ObservableEx.choose(mButler.ask(s), Result::asOption))
                 .map(ButlerResponse::getMessage);

//        mConversation
//                .switchMap(text -> ObservableEx.choose(butler.ask(text), Result::asOption) );
//
//        final Observable<String> validResponse = ObservableEx.choose(butlerResponse, Result::asOption)
//                .map(ButlerResponse::getMessage);

        //subscriptions.add(mConversation.subscribe(mQuestionResponse));
        subscriptions.add(questions.subscribe(a -> Log.e("QQQ", a)));
        subscriptions.add(answers.subscribe(a -> Log.e("QQQ", a)));
       // subscriptions.add(ObservableEx.defineError(butlerResponse).subscribe(mQuestionResponse));
    }

    @NonNull
    public Observable<String> getResponse() {
        return mQuestionResponse;
    }


    public void askButler(@NonNull final String question) {
        mQuestion.onNext(question);
    }
}
