package com.ccong.framework.common.codec;


import com.ccong.framework.common.lang.StringUtils;
import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

public class EscapeUtils {

    private static final Escaper HTML_ESCAPER = HtmlEscapers.htmlEscaper();

    public static String htmlEscape(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }

        return HTML_ESCAPER.escape(text);
    }
}
