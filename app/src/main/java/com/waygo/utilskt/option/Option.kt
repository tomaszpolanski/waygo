package com.waygo.utilskt.option

abstract class Option<T> {

    abstract public val value : T;
    abstract public val isSome : Boolean

    companion object {
        public  fun ofObj<T>(obj : T?) : Option<T> {
            return if (obj != null) Some(obj) else None<T>()
        }
    }
}

class Some<T> internal constructor( val vall : T) : Option<T>() {
    override val isSome: Boolean
        get() = true

    override val value: T
        get() = vall
}

class None<T> internal constructor() : Option<T>() {
    override val isSome: Boolean
        get() = false

    override val value: T
        get() = throw UnsupportedOperationException()

}


fun <IN, OUT> Option<IN>.map( selector:  (IN) -> OUT ) : Option<OUT> =
        when(this) {
            is Some -> Some(selector(this.value))
            else -> None()
        }
