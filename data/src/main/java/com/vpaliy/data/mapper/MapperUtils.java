package com.vpaliy.data.mapper;

import java.util.Arrays;
import java.util.List;

public class MapperUtils {

    public static List<String> splitString(String string){
        if(string==null) return null;
        return Arrays.asList(string.split("\\s*,\\s*"));
    }

    public static String toString(List<String> strings){
        if(strings==null) return null;
        return  strings.toString().replaceAll("[\\[.\\].\\s+]", "");
    }

    public static int convertToInt(String number){
        if(number==null) return 0;
        return Integer.parseInt(number);
    }
}
