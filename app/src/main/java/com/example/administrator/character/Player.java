package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.item.Mall;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;
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
        bag = Item.getSuperDate(playerName);
    }


    private void setTimeDate(GameUI gameUI) {
        timeDate = new GameTime(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(timeDate, 800L, Info.SPEED);
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
        for (Item item:bag.values())
            item.saveSuperDate(playerName);
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
        if (character instanceof Player){
        ((Player)character).setTimeDate(gameUI);
        return;}

     else   character = new Player();
        String[]name =new String[1];
        gameUI.reText("输入你的名字",name);
        //判断是否重复
        Character finalCharacter = character;
        new Response<String>(name){
            @Override
            public void run() {
                while (name[0] == null);
                    finalCharacter.setName(name[0]);
                Sql.operatingSql(new String[]{
                "insert into "+Info.CHARACTER+" ("+Info.id +","+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + ","+Info.coordinate+ ","+Info.salary+ ","+Info.MASTER+ ") values ('"+ Player.class.getName()+"','"+name[0]+"',0,0,0,3000,'"+ null+"')",
                "create table if not exists "+name[0]+Info.INDEX+" (" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)"
                });
                finalCharacter.initialization();
                Character.characters.add(finalCharacter);
                firstPlayGame();
                createPlayerDate(gameUI);
                interrupted();
            }
        }.start();

    }

    public static void firstPlayGame() {
        Character.createNewCharacter(Sql.getDateBase(),StoresEmployee.class.getName(),"蚴牙",1000,200,0,8000,"杂货铺");
        Building building = new Building("杂货铺",0,"蚴牙");
        building.getItems().put("SellItem",new Mall("SellItem",0,0,10,"SellItem"));
    }

    public GameTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(GameTime timeDate) {
        this.timeDate = timeDate;
    }

    public static void setPlayerName(String playerName) {
        Player.playerName = playerName;
    }

    public HashMap<String, Item> getBag() {
        return bag;
    }

    public void setBag(HashMap<String, Item> bag) {
        this.bag = bag;
    }
}

