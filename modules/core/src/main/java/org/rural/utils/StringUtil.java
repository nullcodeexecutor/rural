package org.rural.utils;

/**
 * Created by yuantao on 2014/8/9.
 */
public class StringUtil {
    private StringUtil(){
    }

    public static String cutPrefix(String source, String prefix){
        if(prefix.length()>source.length()){
            return source;
        }
        return source.substring(prefix.length(), source.length());
    }

    public static String cutSuffix(String source, String suffix){
        return source.substring(0, source.length()-suffix.length());
    }

    public static String firstCharToLowerCase(String source){
        if(source.length()<1){
            return "";
        }
        char firstChar = Character.toLowerCase(source.charAt(0));
        return ""+firstChar+source.substring(1, source.length());
    }

    public static String controldlerName(String source, String suffix){
        return firstCharToLowerCase(cutSuffix(source, suffix));
    }

}
