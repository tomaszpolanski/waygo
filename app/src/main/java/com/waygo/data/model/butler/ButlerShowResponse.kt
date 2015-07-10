package com.waygo.data.model.butler

import android.graphics.Bitmap

import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public class ButlerShowResponse private constructor(message: String, public val image: Bitmap) : ButlerResponse(message) {
    companion object {
        public platformStatic fun create(message: String, image: Bitmap): Result<ButlerShowResponse> =
                ButlerResponse.createMessage(message)
                        .map { ButlerShowResponse(it, image) }
    }
}
