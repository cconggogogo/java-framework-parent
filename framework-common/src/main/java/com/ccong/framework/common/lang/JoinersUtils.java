package com.ccong.framework.common.lang;

import com.google.common.base.Joiner;

public class JoinersUtils {

    private JoinersUtils() {

    }

    public static final Joiner COMMA = Joiner.on(',');

    public static final Joiner COLON = Joiner.on(':');

    public static final Joiner SEMI_COLON = Joiner.on(';');
}
