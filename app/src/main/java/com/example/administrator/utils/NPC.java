package com.example.administrator.utils;

import java.util.ArrayList;
import java.util.List;

public interface NPC extends ShowAdapter{

    public static List<NPC> npcs = new ArrayList<>();



    default void startActivity(){

    }

    void work();

    void move();

}
