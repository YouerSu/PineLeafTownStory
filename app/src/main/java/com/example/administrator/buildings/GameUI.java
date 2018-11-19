package com.example.administrator.buildings;

import com.example.administrator.utils.Info;
import com.example.administrator.utils.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameUI{

    static Map<String, String> getAdapterMap(String name, String l1, String l2, String l3) {
        Map<String,String> item = new HashMap<>();
        item.put(Info.INSTANCE.getNAME(),name);
        item.put(Info.INSTANCE.getLT1(),l1);
        item.put(Info.INSTANCE.getLT2(),l2);
        item.put(Info.INSTANCE.getLT3(),l3);
        return item;
    }

    void refreshUI();

    <T> void reText(String message,Response<T> text);

    void dialogueBox(String message);

    <T> void chooseDialogue(String message,T[] messages, Response<T> choose);

    <T extends ShowAdapter> void showListDialogue(List<T> items);

    void dayChange();

    void run(Runnable runnable);
}