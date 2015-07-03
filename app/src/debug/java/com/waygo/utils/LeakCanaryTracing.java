package com.waygo.utils;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import android.app.Application;

public class LeakCanaryTracing implements LeakTracing {

    private RefWatcher refWatcher;

    private final Application application;

    public LeakCanaryTracing(Application application) {
        this.application = application;
    }

    @Override
    public void init() {
        refWatcher = LeakCanary.install(application);
    }

    @Override
    public void traceLeakage(Object reference) {
        refWatcher.watch(reference);
    }
}
