package com.example.administrator.utils;

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


    public static<T extends OwnName> T findMaster(String master, List<T> list) {
        T var = null;
        for (T thing:list){
            if (master.equals(thing.getName())) {
                var = thing;
            }
        }
        if (var!=null) {
            return var;
        }else {
            throw new Error("Didn't find master!");
        }
    }

    public static <T> List<T> toList(Collection<T> collection){
        return new ArrayList<>(collection);
    }

    public static <T> List<T> toList(T[] array){
        return new ArrayList<>(Arrays.asList(array));
    }

}
