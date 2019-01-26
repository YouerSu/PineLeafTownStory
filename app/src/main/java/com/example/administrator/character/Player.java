package com.example.administrator.character;

import com.example.administrator.Init;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;

import java.util.HashMap;

public class Player extends Character {

    public static HashMap<String,Item> bag;
    public static Player player;

    public static void createDate(GameUI gameUI) {
        player = new Player();
        Character finalCharacter = player;
        gameUI.reText("输入你的名字",new Response<String>(){
            //TODO:判断是否重复
            @Override
            public void doThings(String name) {
                finalCharacter.setName(name);
                Sql.operating(new String[]{
                        "insert into "+ Info.INSTANCE.getCHARACTER() +" ("+ Info.INSTANCE.getId() +","+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getMONEY() +","+ Info.INSTANCE.getPRESTIGE() + ","+ Info.INSTANCE.getCoordinate() + ","+ Info.INSTANCE.getSalary() + ","+ Info.INSTANCE.getMASTER() + ") values ('"+ Player.class.getName()+"','"+name+"',0,0,0,3000,'"+ null+"')",
                        "create table if not exists "+name+ Info.INSTANCE.getINDEX() +" (" + Info.INSTANCE.getId() + " text," + Info.INSTANCE.getNAME() + " text," + Info.INSTANCE.getTotal() + " integer)"
                });
                finalCharacter.init();
                getCharacters().add(finalCharacter);
            }
        });
    }

    @Override
    void init() {
        player = this;
        getBagDate();
    }


    private void getBagDate() {
        bag = Item.getIndexDate(getName());
    }


    @Override
    public void saveCharacterDate() {
        super.saveCharacterDate();
        for (Item item:bag.values())
            item.saveIndexDate(getName());
    }


    public static String getPlayerName() {
        return player.getName();
    }
    public static int getX(){ return player.getX_coordinate(); }
    public static void setX(int x){ player.setX_coordinate(x);}

    public static Player getPlayerDate() { return player; }

}