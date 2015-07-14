package com.waygo.utilskt

import kotlin.platform.platformStatic

public abstract class Result<T> {
    abstract public val unsafe : T
    abstract public val isSuccess: Boolean
    abstract public val message : String

    companion object {
        public platformStatic fun ofObj<T>(obj : T?) : Result<T>  = ofObj(obj, "Object is null" )
        public platformStatic fun ofObj<T>(obj : T?, failMessage : String) : Result<T>  =
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
    abstract public fun filter( predicate:  (T) -> Boolean, fail : (T) -> String ) : Result<T>
    abstract public fun orResult( selector:  () -> Result<T> ) : Result<T>
    abstract public fun orDefault( selector:  () -> T ) : T
    abstract public fun toOption() : Option<T>
    abstract public fun <OUT> match( success:  (T) -> OUT, failure: (String) -> OUT) : OUT
}

public class Success<T> constructor( val value : T) : Result<T>() {

    override val message: String
        get() = throw UnsupportedOperationException()
    override val isSuccess: Boolean = true
    override val unsafe: T = value

    override fun <OUT> map(selector: (T) -> OUT): Result<OUT> = Success(selector(value))
    override fun <OUT> flatMap(selector: (T) -> Result<OUT>): Result<OUT>  = selector(value)
    override fun <OUT> ofType(type: Class<OUT>): Result<OUT> =
            if (type.isInstance(value) ) Success(value as OUT) else Failure("Cannot cast to: " + type.toString())
    override fun filter(predicate: (T) -> Boolean, fail: (T) -> String): Result<T> =
            if (predicate(value)) this else Failure(fail(value));
    override fun orResult(selector: () -> Result<T>): Result<T>  = this
    override fun orDefault(selector: () -> T): T = value
    override fun toOption(): Option<T> = Some(value)
    override fun <OUT> match(success: (T) -> OUT, failure: (String) -> OUT): OUT = success(value)
}

public class Failure<T> constructor( override val message : String) : Result<T>() {

    override val unsafe: T
            get() = throw UnsupportedOperationException()
    override val isSuccess: Boolean = false

    override fun <OUT> map(selector: (T) -> OUT): Result<OUT>  = Failure(message)
    override fun <OUT> flatMap(selector: (T) -> Result<OUT>): Result<OUT> = Failure(message)
    override fun <OUT> ofType(type: Class<OUT>): Result<OUT>  = Failure(message)
    override fun filter(predicate: (T) -> Boolean, fail: (T) -> String): Result<T>  = this
    override fun orResult(selector: () -> Result<T>): Result<T>  = selector()
    override fun orDefault(selector: () -> T): T  = selector()
    override fun toOption(): Option<T> = None()
    override fun <OUT> match(success: (T) -> OUT, failure: (String) -> OUT): OUT = failure(message)

}

public inline fun <reified OUT> Result<*>.ofType(): Result<OUT> =
        when (this) {
            is Success -> if (unsafe is OUT) Success(unsafe) else Failure<OUT>("Cannot cast to ${javaClass<OUT>()}")
            else -> Failure<OUT>("Cannot cast to ${javaClass<OUT>()}")
        }
