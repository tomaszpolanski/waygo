package com.waygo.data.model.conversation

import android.graphics.Bitmap

import com.waygo.R
import com.waygo.data.model.butler.ButlerResponse
import com.waygo.data.model.butler.ButlerSayResponse
import com.waygo.data.model.butler.ButlerShowResponse
import com.waygo.utilskt.None
import com.waygo.utilskt.Option
import com.waygo.utilskt.Result

import kotlin.Function0
import kotlin.Function1
import kotlin.platform.platformStatic

public class Waygo : Person {

    public val image: Option<Bitmap>

    public constructor(response: ButlerResponse) : super(response.message, getUserImage(response)) {
        image = getBitmap(response)
    }

    public constructor(message: String) : super(message, Option.ofObj(R.drawable.small_waygo)) {
        image = None<Bitmap>()
    }

    private fun getBitmap(response: ButlerResponse): Option<Bitmap> {
        return Option.ofObj(response)
                .ofType(javaClass<ButlerShowResponse>())
                .map{it.image}
    }

    companion object {
        private platformStatic fun getUserImage(response: ButlerResponse): Option<Int> {
            return Option.ofObj(response)
                    .ofType(javaClass<ButlerSayResponse>())
                    .map {R.drawable.small_waygo}
                    .orOption {Option.ofObj(R.drawable.small_lady)}
        }
    }
}
