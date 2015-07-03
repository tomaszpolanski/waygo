package com.waygo.injections;

import com.waygo.utils.DebugInstrumentation;
import com.waygo.utils.Instrumentation;
import com.waygo.utils.LeakCanaryTracing;
import com.waygo.utils.LeakTracing;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugInstrumentationModule {

    @Provides
    @Singleton
    public Instrumentation providesInstrumentation(LeakTracing leakTracing) {
        return new DebugInstrumentation(leakTracing);
    }

    @Provides
    @Singleton
    public LeakTracing providesLeakTracing(Application application) {
        return new LeakCanaryTracing(application);
    }

}
