package com.example.administrator.character;

import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Info;

import java.util.HashMap;
import java.util.Timer;

public class Player extends Character {

    public GameTime timeDate;
    private static String playerName;
    private HashMap<String,Item> bag;

    @Override
    void initialization() {
        playerName = name;
        getBagDate();
    }

    private void getBagDate() {
        bag = Item.getSuperDate(Info.YOU);
    }


    private void setTimeDate(GameUI gameUI) {
        timeDate = new GameTime(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(timeDate, 3000L, 2000L);
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
        for (Item item:bag.values())
            item.saveSuperDate(Info.YOU);
    }


    public static String getPlayerName() {
        return playerName;
    }

    public static Player getPlayerDate() {
        Character character = getFirstMaster(findMaster(playerName,characters));
        return (Player)character;

    }

    public static void createPlayerDate(GameUI gameUI) {
        Character character = getFirstMaster(findMaster(playerName,characters));
        if (character instanceof Player)
        ((Player)character).setTimeDate(gameUI);
        return;
    }

    public GameTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(GameTime timeDate) {
        this.timeDate = timeDate;
    }



}

