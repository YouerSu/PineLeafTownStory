package com.example.administrator.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Tools {

    public static<T> T getType(String className) {
        T article = null;
        try {
            Class type = Class.forName(className);
            //newInstance会调用构造器
            article = (T) type.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return article;
    }


    public static<T extends OwnName> List<T> findMaster(String master, List<T> list) {
        List<T> things = new ArrayList<>();
        for (T thing:list)
            if (thing.getName()!=null&&master!=null&&master.equals(thing.getName()))
                things.add(thing);
        return things;
    }

    public static <T> List<T> toList(Collection<T> collection){
        List<T> list = new ArrayList<>();
        for (T item:collection)
            list.add(item);
        return list;
    }

    public static <T> List<T> toList(T[] array){
        List<T> list = new ArrayList<>();
        for (T item:array)
            list.add(item);
        return list;
    }

    public static<T> T getFirstMaster(List<T> list){
        for (T master:list)
            return master;
        return null;
    }



}
