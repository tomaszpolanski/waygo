package com.waygo.data.model.conversation;

import android.support.annotation.NonNull;

import com.waygo.utils.option.OptionJ;
import com.waygo.utilskt.None;
import com.waygo.utilskt.Option;

public class User extends Person {

    public User(@NonNull String sentence) {
        super(sentence, new None());
    }
}
