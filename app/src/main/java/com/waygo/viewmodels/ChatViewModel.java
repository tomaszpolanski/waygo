package com.waygo.viewmodels;

import android.support.annotation.NonNull;
import android.util.Log;

import com.waygo.data.model.conversation.Person;
import com.waygo.data.model.conversation.User;
import com.waygo.data.model.conversation.Waygo;
import com.waygo.data.provider.FakeButler;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ISchedulerProvider;
import com.waygo.utils.Linq;
import com.waygo.utils.ObservableEx;
import com.waygo.utils.result.Result;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class ChatViewModel extends AbstractViewModel {

    private static final String TAG = ChatViewModel.class.getSimpleName();

    private final BehaviorSubject<Person> mConversation = BehaviorSubject.create();

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

    @NonNull
    private String getQuestion() {
        final String question = mQuestionsList.first().orDefault(() -> "");
        mQuestionsList = mQuestionsList.skip(1);
        return question;
    }

    @Override
    protected void subscribeToDataStoreInternal(@NonNull CompositeSubscription subscriptions) {
        final Observable<Person> questions =  Observable.interval(3, TimeUnit.SECONDS, mSchedulerProvider.getTimeScheduler())
                .map(__ -> getQuestion())
                .filter(a -> !a.isEmpty())
                .map(sentence -> new User(sentence))
                .ofType(Person.class)
                .share();

        final Observable<Person> answers = questions.switchMap(s -> ObservableEx.choose(mButler.ask(s.getSentence()), Result::asOption))
                 .map(response -> new Waygo(response))
                 .ofType(Person.class);

        final Observable<Person> conversation =
                questions.mergeWith(answers).startWith(new Waygo("Ola, Jen. Welcome to Madrid."));

        subscriptions.add(conversation.subscribe(mConversation));
    }


    @NonNull
    public Observable<Person> getConversation() {
        return mConversation.asObservable();
    }
}
