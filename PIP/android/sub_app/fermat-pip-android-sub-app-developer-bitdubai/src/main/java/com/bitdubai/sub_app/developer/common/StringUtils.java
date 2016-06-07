package com.bitdubai.sub_app.developer.common;

/**
 * Created by mati on 2015.07.08..
 */
public class StringUtils {


    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String replaceStringByPoint(String s) {
        return s.replace(".", " ");

    }

    public static String replaceStringByUnderScore(String s) {
        return s.replace("_", " ");

    }
}
