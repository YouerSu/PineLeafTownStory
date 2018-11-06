package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.item.Mall;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.HashMap;
import java.util.Timer;

public class Player extends Character {

    public static GameTime timeDate;
    private static String playerName;
    private HashMap<String,Item> bag;

    @Override
    void init() {
        playerName = getName();
        getBagDate();
    }


    private void getBagDate() {
        bag = Item.getIndexDate(playerName);
    }


    private void setTimeDate(GameUI gameUI) {
        timeDate = new GameTime(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(timeDate, 800L, Info.INSTANCE.getSPEED());
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
        for (Item item:bag.values())
            item.saveIndexDate(playerName);
    }


    public static String getPlayerName() {
        return playerName;
    }

    public static Player getPlayerDate() {
        Character character = Tools.findMaster(playerName,characters);
        return (Player)character;
    }

    public static void createPlayerDate(GameUI gameUI) {
        Character character = getPlayerDate();
        if (character != null){
            ((Player)character).setTimeDate(gameUI);
            return;
        } else{
            character = new Player();
        }
        String[]name =new String[1];
        gameUI.reText("输入你的名字",name);
        //判断是否重复
        Character finalCharacter = character;
        new Response<String>(name){
            @Override
            public void doThings() {
                finalCharacter.setName(name[0]);
                Sql.operating(new String[]{
                        "insert into "+ Info.INSTANCE.getCHARACTER() +" ("+ Info.INSTANCE.getId() +","+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getMONEY() +","+ Info.INSTANCE.getPRESTIGE() + ","+ Info.INSTANCE.getCoordinate() + ","+ Info.INSTANCE.getSalary() + ","+ Info.INSTANCE.getMASTER() + ") values ('"+ Player.class.getName()+"','"+name[0]+"',0,0,0,3000,'"+ null+"')",
                        "create table if not exists "+name[0]+ Info.INSTANCE.getINDEX() +" (" + Info.INSTANCE.getId() + " text," + Info.INSTANCE.getNAME() + " text," + Info.INSTANCE.getTotal() + " integer)"
                });
                finalCharacter.init();
                Character.characters.add(finalCharacter);
                firstPlayGame();
                createPlayerDate(gameUI);
            }
        };
    }

    private static void firstPlayGame() {
        Character.createNewCharacter(StoresEmployee.class.getName(),"蚴牙",1000,200,0,8000,"杂货铺");
        Building building = new Building("杂货铺",0,"蚴牙");
        building.setItems(Item.getAllItems(Mall.class.getName()));
    }

    public HashMap<String, Item> getBag() {
        return bag;
    }

    public void setBag(HashMap<String, Item> bag) {
        this.bag = bag;
    }
}

