package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;

import com.waygo.data.model.BaseModel;
import com.waygo.utils.option.Option;

public abstract class Person extends BaseModel {
    @NonNull
    private final String mSentence;

    @NonNull
    private final Option<Integer> mUserImage;

    protected Person(@NonNull final String sentence,
                     @NonNull final Option<Integer> userImage) {
        mSentence = sentence;
        mUserImage = userImage;
    }

    @NonNull
    public String getSentence() {
        return mSentence;
    }

    @NonNull
    public Option<Integer> getUserImage() {
        return mUserImage;
    }

    @Override
    public String toString() {
        return getString("Sentence", getSentence());
    }
}
