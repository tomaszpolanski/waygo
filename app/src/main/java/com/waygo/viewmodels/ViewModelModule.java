package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ILogBoxProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    public RepositoryViewModel provideRepositoryViewModel(DataLayer.GetFlightStatus getFlightStatus,
                                                          DataLayer.FetchAndGetGetFlightStatus fetchAndGetGetFlightStatus,
                                                          ILogBoxProvider logBoxProvider,
                                                          IButler butler) {
        return new RepositoryViewModel(getFlightStatus, fetchAndGetGetFlightStatus, logBoxProvider, butler);
    }

    @Provides
    public ChatViewModel provideChatViewModel( IButler butler) {
        return new ChatViewModel( butler);
    }

}
