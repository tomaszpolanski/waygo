package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.provider.interfaces.ILogBoxProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    public RepositoryViewModel provideRepositoryViewModel(DataLayer.GetFlightStatus getFlightStatus,
                                                          DataLayer.FetchAndGetGetFlightStatus fetchAndGetGetFlightStatus,
                                                          ILogBoxProvider logBoxProvider) {
        return new RepositoryViewModel(getFlightStatus, fetchAndGetGetFlightStatus, logBoxProvider);
    }

}
