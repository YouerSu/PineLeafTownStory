package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

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

    public static Player getPlayerDate(GameUI gameUI) {
        Character character = getFirstMaster(findMaster(playerName,characters));
            if (character ==null) {
                character = new Player();
                String name = gameUI.reName("输入你的名字");
                //判断是否重复
                character.setName(name);
                Sql.operatingSql(new String[]{
                "insert into "+ Info.CHARACTER +"("+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + ","+Info.coordinate+ ") values("+name+",0,0,0)"
                });
                Character.characters.add(character);
            }
        return (Player)character;

    }

    public GameTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(GameTime timeDate) {
        this.timeDate = timeDate;
    }
}

