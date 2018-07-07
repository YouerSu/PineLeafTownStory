package com.example.administrator.utils;

import android.database.Cursor;
import com.example.administrator.buildings.Character;
import java.util.LinkedList;

public class Player{

    public static LinkedList<Character> characters;

    public int money;
    public int prestige;
    public String name;
    private int x_coordinate;

    public void saveDate(String name) {
        GameTime.operatingSql(new String[]
        {
        "update "+Info.PLAYER+" set "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" "+Info.PRESTIGE+" = "+x_coordinate+" where "+Info.NAME+" = "+ this.name
        });



    }

    public void saveCharacterDate(String name){
        for (Character character:characters)
            character.saveDate(name);
    }

    public void getCharacterDate(String name) {
            Character character = new Character();
            character.getDate(GameTime.getCursorAllInformation(Info.CHARACTER+Info.PLAYER));

    }

    public void getDate(Cursor iDate) {
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setMoney(iDate.getInt(iDate.getColumnIndex(Info.MONEY)));
        setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }


}

