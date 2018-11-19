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
import java.util.TimerTask;

public class Player extends Character {

    public static GameTime timeDate;
    public static HashMap<String,Item> bag;
    public static Player player;

    @Override
    void init() {
        player = this;
        getBagDate();
    }


    private void getBagDate() {
        bag = Item.getIndexDate(getName());
    }


    private void setTimeDate(GameUI gameUI) {
        timeDate = new GameTime(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                gameUI.run(timeDate);
            }
        }, 800L, Info.INSTANCE.getSPEED());
    }

    @Override
    public void saveDate() {
        super.saveDate();
        timeDate.saveDate();
        for (Item item:bag.values())
            item.saveIndexDate(getName());
    }


    public static String getPlayerName() {
        return player.getName();
    }
    public static int getX(){ return player.getX_coordinate(); }
    public static void setX(int x){ player.setX_coordinate(x);}

    public static Player getPlayerDate() { return player; }

    public static void createPlayerDate(GameUI gameUI) {
        Player player = getPlayerDate();
        if (player != null){
            player.setTimeDate(gameUI);
            return;
        } else{
            player = new Player();
        }
        //TODO:判断是否重复
        Character finalCharacter = player;

        gameUI.reText("输入你的名字",new Response<String>(){
            @Override
            public void doThings(String name) {
                finalCharacter.setName(name);
                Sql.operating(new String[]{
                        "insert into "+ Info.INSTANCE.getCHARACTER() +" ("+ Info.INSTANCE.getId() +","+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getMONEY() +","+ Info.INSTANCE.getPRESTIGE() + ","+ Info.INSTANCE.getCoordinate() + ","+ Info.INSTANCE.getSalary() + ","+ Info.INSTANCE.getMASTER() + ") values ('"+ Player.class.getName()+"','"+name+"',0,0,0,3000,'"+ null+"')",
                        "create table if not exists "+name+ Info.INSTANCE.getINDEX() +" (" + Info.INSTANCE.getId() + " text," + Info.INSTANCE.getNAME() + " text," + Info.INSTANCE.getTotal() + " integer)"
                });
                finalCharacter.init();
                Character.characters.add(finalCharacter);
                firstPlayGame();
                createPlayerDate(gameUI);
            }
        });
    }

    private static void firstPlayGame() {
        Character.createNewCharacter(StoresEmployee.class.getName(),"蚴牙",1000,200,0,8000,"杂货铺");
        Building building = new Building("杂货铺",0,"蚴牙");
        building.setItems(Item.changeToMap(Mall.items));
    }
}