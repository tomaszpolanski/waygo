package com.waygo.injections;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import com.waygo.data.provider.FakeLocationProvider;
import com.waygo.data.provider.FakeLogBoxProvider;
import com.waygo.data.provider.SchedulerProvider;
import com.waygo.data.provider.interfaces.ILocationProvider;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    ISchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @Singleton
    ILocationProvider provideLocationProvider(ISchedulerProvider schedulerProvider) {
        return new FakeLocationProvider(schedulerProvider);
    }

    @Provides
    @Singleton
    ILogBoxProvider providesLogBoxProvider(ISchedulerProvider schedulerProvider) {
        return new FakeLogBoxProvider(schedulerProvider);
    }

    @Provides
    @Singleton
    public ContentResolver contentResolver(@ForApplication Context context) {
        return context.getContentResolver();
    }

}
