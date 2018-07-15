package com.example.administrator.buildings;

import com.example.administrator.utils.Info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameUI{

    static Map<String, String> getAdapterMap(String name, String l1, String l2, String l3) {
        Map<String,String> item = new HashMap<>();
        item.put(Info.NAME,name);
        item.put(Info.LT1,l1);
        item.put(Info.LT2,l2);
        item.put(Info.LT3,l3);
        return item;
    }

    void refreshUI();

    void dayHarvest();

    <T> void reText(String message,T[] list);

    void dialogueBox(String message);

    <T> void chooseDialogue(T[] messages, T[] choose);

    <T extends ShowAdapter> void showListDialogue(List<T> items);



}
