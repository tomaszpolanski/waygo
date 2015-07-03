package com.waygo.viewmodels;

import com.waygo.data.DataLayer.FetchAndGetGitHubRepository;
import com.waygo.data.DataLayer.GetGitHubRepository;
import com.waygo.data.DataLayer.GetGitHubRepositorySearch;
import com.waygo.data.DataLayer.GetUserSettings;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    public RepositoriesViewModel provideRepositoriesViewModel(GetGitHubRepositorySearch repositorySearch,
                                                              GetGitHubRepository repositoryRepository) {
        return new RepositoriesViewModel(repositorySearch, repositoryRepository);
    }

    @Provides
    public RepositoryViewModel provideRepositoryViewModel(GetUserSettings getUserSettings,
                                                          FetchAndGetGitHubRepository fetchAndGetGitHubRepository) {
        return new RepositoryViewModel(getUserSettings, fetchAndGetGitHubRepository);
    }

}
