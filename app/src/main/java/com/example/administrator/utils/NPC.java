package com.example.administrator.utils;

import java.util.ArrayList;
import java.util.List;

public interface NPC extends ShowAdapter{

    public static List<NPC> npcs = new ArrayList<>();

    //开启线程
    default void startActivity(){

    }

    //NPC的行为
    void work();


}
