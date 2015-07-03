package com.waygo.utils;


import java.util.Random;

public class Rand {

    public static int randInt(int min, int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
