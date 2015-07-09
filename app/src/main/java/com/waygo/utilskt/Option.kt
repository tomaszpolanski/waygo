package com.waygo.utilskt

import kotlin.platform.platformStatic

abstract class Option<T> {

    abstract public val unsafe : T;
    abstract public val isSome : Boolean

    companion object {
        public platformStatic fun ofObj<T>(obj : T?) : Option<T>  = if (obj != null) Some(obj) else None<T>()
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
    abstract public fun <OUT> ofType( type:  Class<OUT> ) : Option<OUT>
    abstract public fun filter( predicate:  (T) -> Boolean ) : Option<T>
    abstract public fun orOption( selector:  () -> Option<T> ) : Option<T>
    abstract public fun orDefault( selector:  () -> T ) : T
    abstract public fun toList() : List<T>
    abstract public fun <OUT> match( some:  (T) -> OUT, none: () -> OUT) : OUT
}

class Some<T> internal constructor( val value : T) : Option<T>() {

    override val isSome: Boolean = true

    override val unsafe: T = value

    override fun <OUT> map( selector:  (T) -> OUT ) : Option<OUT> = Some(selector(value))
    override fun <OUT> flatMap(selector: (T) -> Option<OUT>): Option<OUT> = selector(value)
    override fun <OUT> ofType(type: Class<OUT>): Option<OUT> = if (type.isInstance(value)) Some(value as OUT) else None()
    override fun filter(predicate: (T) -> Boolean): Option<T> = if (predicate(value)) this else None();
    override fun orOption(selector: () -> Option<T>): Option<T> = this
    override fun orDefault(selector: () -> T): T = value
    override fun toList(): List<T> = listOf(value)
    override fun <OUT> match(some: (T) -> OUT, none: () -> OUT): OUT = some(value)

}

class None<T> internal constructor() : Option<T>() {

    override val isSome: Boolean = false

    override val unsafe: T
        get() = throw UnsupportedOperationException()

    override fun <OUT> map(selector: (T) -> OUT): Option<OUT> = None()
    override fun <OUT> flatMap(selector: (T) -> Option<OUT>): Option<OUT> = None()
    override fun <OUT> ofType(type: Class<OUT>): Option<OUT>  = None()
    override fun filter(predicate: (T) -> Boolean): Option<T> = this
    override fun orOption(selector: () -> Option<T>): Option<T> = selector()
    override fun orDefault(selector: () -> T): T = selector()
    override fun toList(): List<T> = emptyList()
    override fun <OUT> match(some: (T) -> OUT, none: () -> OUT): OUT = none()
}
