package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;

import com.waygo.data.model.BaseModel;

public abstract class Person extends BaseModel {
    @NonNull
    private final String mSentence;

    protected Person(@NonNull final String sentence) {
        mSentence = sentence;
    }

    @NonNull
    public String getSentence() {
        return mSentence;
    }

    @Override
    public String toString() {
        return getString("Sentence", getSentence());
    }
}
