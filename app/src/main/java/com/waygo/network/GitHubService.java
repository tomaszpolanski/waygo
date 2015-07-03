package com.waygo.network;

import com.waygo.pojo.GitHubRepository;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface GitHubService {
    @GET("/search/repositories")
    GitHubRepositorySearchResults search(
            @QueryMap Map<String, String> search
    );

    @GET("/repositories/{id}")
    GitHubRepository getRepository(@Path("id") Integer id);
}
