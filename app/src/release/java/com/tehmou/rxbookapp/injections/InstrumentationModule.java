package com.waygo.injections;

import com.waygo.utils.Instrumentation;
import com.waygo.utils.NullInstrumentation;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InstrumentationModule {

    @Provides
    @Singleton
    public Instrumentation providesInstrumentation(@ForApplication Context context) {
        return new NullInstrumentation(context);
    }

}
