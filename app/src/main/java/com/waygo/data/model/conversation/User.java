package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;

import com.waygo.utils.option.OptionJ;

public class User extends Person {

    public User(@NonNull String sentence) {
        super(sentence, OptionJ.NONE_J);
    }
}
