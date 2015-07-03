package com.waygo;

import com.waygo.injections.Graph;
import com.waygo.utils.Instrumentation;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class WaygoApplication extends Application {

    private static WaygoApplication instance;

    private Graph mGraph;

    @Inject
    Instrumentation instrumentation;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mGraph = Graph.Initializer.init(this);
        getGraph().inject(this);

        instrumentation.init();
    }

    @NonNull
    public static WaygoApplication getInstance() {
        return instance;
    }

    @NonNull
    public Graph getGraph() {
        return mGraph;
    }

}
