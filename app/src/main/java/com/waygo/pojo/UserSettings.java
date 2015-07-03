package com.waygo.pojo;

public class UserSettings {
    private final int selectedRepositoryId;

    public UserSettings(int selectedRepositoryId) {
        this.selectedRepositoryId = selectedRepositoryId;
    }

    public int getSelectedRepositoryId() {
        return selectedRepositoryId;
    }
}
