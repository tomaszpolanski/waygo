package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;

import com.waygo.utils.option.Option;

public class User extends Person {

    public User(@NonNull String sentence) {
        super(sentence, Option.NONE);
    }
}
