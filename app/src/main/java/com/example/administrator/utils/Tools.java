package com.example.administrator.utils;

import com.example.administrator.buildings.Building;
import com.example.administrator.character.Employee;
import com.example.administrator.character.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Tools {

    public static<T> T getType(String className) {
        T article;
        try {
            Class type = Class.forName(className);
            //newInstance会调用构造器
            article = (T) type.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new Error("Can't found class "+className);
        }
        return article;
    }

    public static String getSuffix(String str){
        return str.substring(str.lastIndexOf(".")+1);
    }

    public static <T> List<T> toList(Collection<T> collection){
        return new ArrayList<>(collection);
    }

    public static <T> List<T> toList(T[] array){
        return Arrays.asList(array);
    }

}
