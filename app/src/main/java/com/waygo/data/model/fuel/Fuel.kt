package com.waygo.data.model.fuel


import com.waygo.utilskt.Result
import kotlin.platform.platformStatic

public abstract class Fuel protected constructor(public val value: Float) {
    companion object {

        protected platformStatic fun createValue(value: Float): Result<Float> =
                Result.ofObj(value).filter({ it >= 0 && it <= 1 }) { "Fuel value is invalid: ${it}" }

    }
}