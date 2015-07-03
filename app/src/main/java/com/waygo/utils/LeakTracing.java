package com.waygo.utils;

public interface LeakTracing {

    void init();

    void traceLeakage(Object reference);

}
