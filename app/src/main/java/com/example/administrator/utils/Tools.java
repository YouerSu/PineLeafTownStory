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


    public static<T extends OwnName> T findMaster(String master, List<T> list) {
        T var = null;
        for (T thing:list){
            if (master.equals(thing.getName())) {
                var = thing;
            }
        }
        return var;
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

    public static<T extends OwnMaster> boolean isPlayerEmployee(T employee) {
        String master = employee.getMaster();
        String playerName = Player.getPlayerName();
        if (master.equals(playerName)) return true;
        else{
            Building workSpace;
            workSpace = Building.findWorkSpace(master);
            if (workSpace!=null)
                master = workSpace.getMaster();
        }
        return master.equals(playerName);
    }
}
