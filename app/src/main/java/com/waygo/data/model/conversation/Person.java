package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.waygo.data.model.BaseModel;
import com.waygo.utilskt.Option;

public abstract class Person extends BaseModel {
    @NonNull
    private final String mSentence;

    @NonNull
    private final Option<Integer> mUserImage;

    @NonNull
    private final String mTime;


    protected Person(@NonNull final String sentence,
                     @NonNull final Option<Integer> userImage) {
        mSentence = sentence;
        mUserImage = userImage;

        mTime = DateFormat.format("HH:mm:ss", new java.util.Date()).toString();
    }

    @NonNull
    public String getSentence() {
        return mSentence;
    }

    @NonNull
    public Option<Integer> getUserImage() {
        return mUserImage;
    }

    @NonNull
    public String getTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return getString("Sentence", getSentence());
    }
}
