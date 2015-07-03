package com.waygo.network;

import com.waygo.pojo.GitHubRepository;

import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;

public class NetworkApi {
    private final GitHubService gitHubService;

    public NetworkApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .build();
        gitHubService = restAdapter.create(GitHubService.class);
    }

    public List<GitHubRepository> search(Map<String, String> search) {
        GitHubRepositorySearchResults results = gitHubService.search(search);
        return results.getItems();
    }

    public GitHubRepository getRepository(int id) {
        return gitHubService.getRepository(id);
    }
}
