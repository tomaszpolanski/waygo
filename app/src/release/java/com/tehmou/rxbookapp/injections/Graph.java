package com.waygo.injections;

import com.waygo.WaygoApplication;
import com.waygo.injections.DaggerGraph;
import com.waygo.activities.MainActivity;
import com.waygo.data.DataStoreModule;
import com.waygo.fragments.RepositoriesFragment;
import com.waygo.fragments.RepositoryFragment;
import com.waygo.network.NetworkService;
import com.waygo.viewmodels.RepositoriesViewModel;
import com.waygo.viewmodels.RepositoryViewModel;
import com.waygo.viewmodels.ViewModelModule;
import com.waygo.widget.WidgetService;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataStoreModule.class, ViewModelModule.class,
                      InstrumentationModule.class})
public interface Graph {

    void inject(RepositoriesViewModel repositoriesViewModel);
    void inject(RepositoryViewModel widgetService);
    void inject(WidgetService widgetService);
    void inject(MainActivity mainActivity);
    void inject(RepositoriesFragment repositoriesFragment);
    void inject(RepositoryFragment repositoryFragment);
    void inject(NetworkService networkService);
    void inject(WaygoApplication waygoApplication);

    final class Initializer {

        public static Graph init(Application application) {
            return DaggerGraph.builder()
                              .applicationModule(new ApplicationModule(application))
                              .build();
        }
    }
}
