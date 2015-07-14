package com.waygo.utils


import java.util.Random

public object Rand {
    public fun randInt(min: Int, max: Int): Int = Random().nextInt((max - min) + 1) + min
}
