package com.waygo.utilskt

import kotlin.platform.platformStatic

abstract class Result<T> {
    abstract public val unsafe : T
    abstract public val isSuccess: Boolean
    abstract public val message : String

    companion object {
        public platformStatic fun ofObj<T>(obj : T?, failMessage : String = "Object is null") : Result<T>  =
                if (obj != null) Success(obj) else Failure<T>(failMessage)
        public platformStatic fun tryAsOption<T>(f : () -> T ) : Result<T>  {
            try {
                return ofObj(f())
            } catch ( e : Exception) {
                return Failure(e.toString())
            }
        }
    }

    abstract public fun <OUT> map( selector:  (T) -> OUT ) : Result<OUT>
    abstract public fun <OUT> flatMap( selector:  (T) -> Result<OUT> ) : Result<OUT>
    abstract public fun <OUT> ofType( type:  Class<OUT> ) : Result<OUT>
    abstract public fun filter( predicate:  (T) -> Boolean, fail : String ) : Result<T>
    abstract public fun orResult( selector:  () -> Result<T> ) : Result<T>
    abstract public fun orDefault( selector:  () -> T ) : T
    abstract public fun <OUT> match( success:  (T) -> OUT, failure: (String) -> OUT) : OUT
}

class Success<T> internal constructor( val value : T) : Result<T>() {

    override val message: String = throw UnsupportedOperationException()
    override val isSuccess: Boolean = true
    override val unsafe: T = value

    override fun <OUT> map(selector: (T) -> OUT): Result<OUT> = Success(selector(value))
    override fun <OUT> flatMap(selector: (T) -> Result<OUT>): Result<OUT>  = selector(value)
    override fun <OUT> ofType(type: Class<OUT>): Result<OUT> =
            if (type.isInstance(value)) Success(value as OUT) else Failure("Cannot cast to: " + type.toString())
    override fun filter(predicate: (T) -> Boolean, fail: String): Result<T> =
            if (predicate(value)) this else Failure(fail);
    override fun orResult(selector: () -> Result<T>): Result<T>  = this
    override fun orDefault(selector: () -> T): T = value
    override fun <OUT> match(success: (T) -> OUT, failure: (String) -> OUT): OUT = success(value)
}

class Failure<T> internal constructor( override val message : String) : Result<T>() {

    override val unsafe: T  = throw UnsupportedOperationException()
    override val isSuccess: Boolean = false

    override fun <OUT> map(selector: (T) -> OUT): Result<OUT>  = Failure(message)
    override fun <OUT> flatMap(selector: (T) -> Result<OUT>): Result<OUT> = Failure(message)
    override fun <OUT> ofType(type: Class<OUT>): Result<OUT>  = Failure(message)
    override fun filter(predicate: (T) -> Boolean, fail: String): Result<T>  = this
    override fun orResult(selector: () -> Result<T>): Result<T>  = selector()
    override fun orDefault(selector: () -> T): T  = selector()
    override fun <OUT> match(success: (T) -> OUT, failure: (String) -> OUT): OUT = failure(message)

}