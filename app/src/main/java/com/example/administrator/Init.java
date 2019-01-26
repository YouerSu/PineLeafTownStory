package com.example.administrator;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.character.Character;
import com.example.administrator.character.Player;
import com.example.administrator.character.StoresEmployee;
import com.example.administrator.item.Item;
import com.example.administrator.item.Mall;

public final class Init {
    public static void init(GameUI game) {
        Init.getAllDate();
        createPlayerDate(game);
        GameTime.setTimeDate(game);
    }

    private static void getAllDate(){
        Building.getDate();
        Character.getDate();
    }

    public static void saveAllDate() {
        GameTime.timeDate.saveDate();
        Building.saveDate();
        Character.saveDate();
    }

    private static void createPlayerDate(GameUI gameUI) {
        if (Player.getPlayerDate() == null){
            Player.createDate(gameUI);
            Init.firstPlayGame();
            //Init.createPlayerDate(gameUI);
        }
    }

    private static void firstPlayGame() {
        Character.createNewCharacter(StoresEmployee.class.getName(),"蚴牙",1000,200,0,8000,"杂货铺");
        Building building = new Building("杂货铺",0,"蚴牙");
        building.setItems(Item.changeToMap(Mall.items));
    }

}
