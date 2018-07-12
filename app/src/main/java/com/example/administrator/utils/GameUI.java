package com.example.administrator.utils;

import android.widget.TextView;

import com.example.administrator.buildings.Building;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameUI {

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

    <T> void reName(String message,T[] list);

    int reAmount(String message);

    void dialogueBox(String message);

    boolean trueOrFalseDialogue(String message);

    void showMyOwnListDialogue(List<ShowAdapter> items);

    void showNotMyOwnListDialogue(List<ShowAdapter> items);


}
