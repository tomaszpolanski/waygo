package com.waygo.utils;

public class NullLeakTracing implements LeakTracing {

    @Override
    public void init() { }

    @Override
    public void traceLeakage(Object reference) { }
}
