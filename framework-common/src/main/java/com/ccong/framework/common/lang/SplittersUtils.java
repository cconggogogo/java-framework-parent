package com.ccong.framework.common.lang;

import com.google.common.base.Splitter;

public class SplittersUtils {

    private SplittersUtils() {

    }


    public static final Splitter COMMA = Splitter.on(',').trimResults().omitEmptyStrings();

    public static final Splitter COLON = Splitter.on(':').trimResults().omitEmptyStrings();

    public static final Splitter DOT = Splitter.on('.').trimResults().omitEmptyStrings();

    public static final Splitter SEMI_COLON = Splitter.on(';').trimResults().omitEmptyStrings();
}
