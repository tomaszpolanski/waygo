package com.waygo.pojo;

import java.util.List;

public class GitHubRepositorySearch {
    final private String search;
    final private List<Integer> items;

    public GitHubRepositorySearch(final String search, final List<Integer> items) {
        this.search = search;
        this.items = items;
    }

    public String getSearch() {
        return search;
    }

    public List<Integer> getItems() {
        return items;
    }
}
