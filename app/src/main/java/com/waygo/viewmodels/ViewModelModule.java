package com.waygo.viewmodels;

import com.waygo.data.DataLayer;
import com.waygo.data.provider.interfaces.IButler;
import com.waygo.data.provider.interfaces.ILogBoxProvider;
import com.waygo.data.provider.interfaces.ISchedulerProvider;

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
    public ChatViewModel provideChatViewModel( IButler butler, ISchedulerProvider schedulerProvider) {
        return new ChatViewModel(butler, schedulerProvider);
    }

    @Provides
    public AgendaViewModel provideAgendaViewModel(DataLayer.FetchAndGetGetFlightStatus fetchAndGetFlight) {
        return new AgendaViewModel(fetchAndGetFlight);
    }

}
