package com.example.administrator.utils;

import android.widget.TextView;

import com.example.administrator.buildings.Building;

import java.util.List;

public interface GameUI {

    void refreshUI();

    void dayHarvest();

    void reName(OwnName item);

    void dialogueBox(String message);

    boolean trueOrFalseDialogue(String message);

    void showStockDialogue(List<ShowAdapter> items);

}
