package com.waygo.utilskt

import kotlin.platform.platformStatic

public abstract class Option<T> {

    abstract public val unsafe : T;
    abstract public val isSome : Boolean

    companion object {
        public platformStatic fun ofObj<T>(obj : T) : Option<T>  = if (obj != null) Some(obj) else None<T>()
        public platformStatic fun tryAsOption<T>(f : () -> T ) : Option<T>  {
            try {
                return ofObj(f())
            } catch ( e : Exception) {
                return None();
            }
        }
    }

    abstract public fun <OUT> map( selector:  (T) -> OUT ) : Option<OUT>
    abstract public fun <OUT> flatMap( selector:  (T) -> Option<OUT> ) : Option<OUT>
    abstract public fun filter( predicate:  (T) -> Boolean ) : Option<T>
    abstract public fun orOption( selector:  () -> Option<T> ) : Option<T>
    abstract public fun orDefault( selector:  () -> T ) : T
    abstract public fun toResult(fail: String) : Result<T>
    abstract public fun toSequence() : Sequence<T>
    abstract public fun <OUT> match( some:  (T) -> OUT, none: () -> OUT) : OUT
    abstract public fun iter(some: (T) -> Unit) : Unit
}

public class Some<T> constructor( val value : T) : Option<T>() {


    override val isSome: Boolean = true

    override val unsafe: T = value

    override fun <OUT> map( selector:  (T) -> OUT ) : Option<OUT> = Some(selector(value))
    override fun <OUT> flatMap(selector: (T) -> Option<OUT>): Option<OUT> = selector(value)
    override fun filter(predicate: (T) -> Boolean): Option<T> = if (predicate(value)) this else None();
    override fun orOption(selector: () -> Option<T>): Option<T> = this
    override fun orDefault(selector: () -> T): T = value
    override fun toResult(fail: String): Result<T>  = Success(value)
    override fun toSequence() : Sequence<T> = sequenceOf(value)
    override fun <OUT> match(some: (T) -> OUT, none: () -> OUT): OUT = some(value)
    override fun iter(some: (T) -> Unit) = some(value)
}

public class None<T> constructor() : Option<T>() {


    override val isSome: Boolean = false

    override val unsafe: T
        get() = throw UnsupportedOperationException()

    override fun <OUT> map(selector: (T) -> OUT): Option<OUT> = None()
    override fun <OUT> flatMap(selector: (T) -> Option<OUT>): Option<OUT> = None()
    override fun filter(predicate: (T) -> Boolean): Option<T> = this
    override fun orOption(selector: () -> Option<T>): Option<T> = selector()
    override fun orDefault(selector: () -> T): T = selector()
    override fun toResult(fail: String): Result<T>  = Failure(fail)
    override fun toSequence() : Sequence<T> = emptySequence()
    override fun <OUT> match(some: (T) -> OUT, none: () -> OUT): OUT = none()
    override fun iter(some: (T) -> Unit) = Unit
}

public inline fun <reified OUT> Option<*>.ofType(): Option<OUT> =
        when (this) {
            is Some -> if (this.unsafe is OUT) Some(unsafe) else None()
            else -> None<OUT>()
        }
