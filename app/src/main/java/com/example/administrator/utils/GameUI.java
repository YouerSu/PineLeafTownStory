package com.example.administrator.utils;

import android.widget.TextView;

import com.example.administrator.buildings.Building;

import java.util.List;

public interface GameUI {

    void refreshUI();

    void dayHarvest();

    <T> void reName(String message,T[] list);

    int reAmount(String message);

    void dialogueBox(String message);

    boolean trueOrFalseDialogue(String message);

    void showMyOwnListDialogue(List<ShowAdapter> items);

    void showNotMyOwnListDialogue(List<ShowAdapter> items);


}
