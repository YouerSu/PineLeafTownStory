package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;

import java.util.Timer;

public class Player extends Character{

    public GameTime timeDate;
    private static String playerName;

    @Override
    void initialization() {
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(timeDate, 3000L, 2000L);
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
    }


    public static String getPlayerName() {
        return playerName;
    }

    public static Player getPlayerDate() {
        for (Character character:findMaster(playerName,characters))
            if (character instanceof Player)
                return (Player)character;
        return null;
    }

    public static void setPlayerName(String name) {

    }

    public GameTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(GameTime timeDate) {
        this.timeDate = timeDate;
    }
}

