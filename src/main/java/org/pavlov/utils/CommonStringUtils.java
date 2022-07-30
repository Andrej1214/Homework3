package org.pavlov.utils;

public class CommonStringUtils {
    public static String replaceCommaOnDot(String value){
        return value.trim().replace(',', '.');
    }
}
