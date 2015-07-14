package com.waygo.data.provider


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import com.waygo.R
import com.waygo.data.model.butler.ButlerResponse
import com.waygo.data.model.butler.ButlerSayResponse
import com.waygo.data.model.butler.ButlerShowResponse
import com.waygo.data.provider.interfaces.IButler
import com.waygo.data.provider.interfaces.ISchedulerProvider
import com.waygo.utilskt.Failure
import com.waygo.utilskt.Result
import com.waygo.utilskt.ofType
import java.util.concurrent.TimeUnit

import rx.Observable
import rx.Subscriber

public class FakeButler(private val mResources: Resources, private val mSchedulerProvider: ISchedulerProvider) : IButler {

    override fun ask(question: String): Observable<Result<ButlerResponse>> {
        return Observable.create(object : Observable.OnSubscribe<Result<ButlerResponse>> {
            override fun call(subscriber: Subscriber<in Result<ButlerResponse>>) {
                subscriber.onNext(getResponse(question))
            }
        }).debounce(1, TimeUnit.SECONDS, mSchedulerProvider.getTimeScheduler())
    }

    private fun getResponse(question: String): Result<ButlerResponse> {
        when (question) {
            HUNGRY -> return ButlerSayResponse.create("Hi, Hungry I thought your name was Jenny, Ha Ha :P So, Vegan like last time?")
                    .ofType<ButlerResponse>()
            TOILETS -> return ButlerSayResponse.create("Next one is twenty metres on the left. ")
                    .ofType<ButlerResponse>()
            CHEAPGOOD -> return ButlerSayResponse.create("Ok, There is <a href='http://www.beans.com'> Natural Beans </a> which is mid priced with four stars on foursquare? ")
                    .ofType<ButlerResponse>()
            MAP -> return getBitmap(R.drawable.map, mResources)
                    .flatMap { ButlerShowResponse.create("Here's the map.", it)
                                  .ofType<ButlerResponse>() }
            else -> return Failure("Sorry, I didn't get that")
        }
    }

    companion object {

        public val TOILETS: String = "Waygo, where are the toilets?"
        public val HUNGRY: String = "Waygo, I'm Hungry."
        public val CHEAPGOOD: String = "Yeah, That would be great something cheap and good."
        public val MAP: String = "Yeah, perfect how do I get there?"
        public val CHEERS: String = "Cheers Waygo."

        private fun getBitmap(id: Int, resources: Resources): Result<Bitmap> =
                Result.ofObj(BitmapFactory.decodeResource(resources, id))
    }
}
