package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;

import java.util.Timer;

public class Player extends Character{

    public GameTime timeDate;

    public Player(GameUI gameUI) {
        super(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(timeDate, 3000L, 2000L);
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
    }
}

