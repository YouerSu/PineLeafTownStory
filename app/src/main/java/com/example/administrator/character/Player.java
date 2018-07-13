package com.example.administrator.character;

import android.database.Cursor;

import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

import java.util.HashMap;
import java.util.List;
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
//        character = new Player();
//        String[]name =new String[1];
//        gameUI.reName("输入你的名字",name);
//        //判断是否重复
//        Character finalCharacter = character;
//        new Response<String>(name){
//            @Override
//            public void run() {
//                while (name[0] == null);
//                    finalCharacter.setName(name[0]);
//                Sql.operatingSql(new String[]{
//                        "insert into "+ Info.CHARACTER +"("+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + ","+Info.coordinate+ ")values("+name[0]+",0,0,0)"
//                });
//                finalCharacter.initialization();
//                Character.characters.add(finalCharacter);
//                createPlayerDate(gameUI);
//                gameUI.dialogueBox("设置成功");
//                interrupted();
//            }
//        }.start();

    }

    public GameTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(GameTime timeDate) {
        this.timeDate = timeDate;
    }
}

