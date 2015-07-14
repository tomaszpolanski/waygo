package com.waygo.viewmodels


import com.waygo.data.model.butler.ButlerResponse
import com.waygo.data.model.conversation.Person
import com.waygo.data.model.conversation.User
import com.waygo.data.model.conversation.Waygo
import com.waygo.data.provider.FakeButler
import com.waygo.data.provider.interfaces.IButler
import com.waygo.data.provider.interfaces.ISchedulerProvider
import com.waygo.utils.ObservableEx
import com.waygo.utilskt.Option
import com.waygo.utilskt.Result
import com.waygo.utilskt.ofType
import rx.Observable
import rx.functions.Func1
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription

public class ChatViewModel(private val mButler: IButler, private val mSchedulerProvider: ISchedulerProvider) : AbstractViewModel() {

    private val mConversation = BehaviorSubject.create<Person>()

    fun getQuestions() =
            listOf(FakeButler.TOILETS, FakeButler.CHEERS, FakeButler.HUNGRY, FakeButler.CHEAPGOOD, FakeButler.MAP)

    override fun subscribeToDataStoreInternal(subscriptions: CompositeSubscription) {
        val questions = ObservableEx.delayEach(getQuestions(), 3, mSchedulerProvider.getTimeScheduler())
                .map{User(it)}
                .ofType<Person>()
                .share()

        val answers = questions.switchMap{ObservableEx.chooseKt(mButler.ask(it.sentence), {op -> op.toOption()})}
                .map{Waygo(it)}
                .ofType<Person>()

        val conversation = questions.mergeWith(answers)
                .startWith(Waygo("Ola, Jen. Welcome to Madrid."))

        subscriptions.add(conversation.subscribe(mConversation))
    }


    public fun getConversation(): Observable<Person> {
        return mConversation.asObservable()
    }
}
