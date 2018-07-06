package com.example.administrator.utils;

import android.database.Cursor;

public class Player{

    public int money;
    public int prestige;
    public String name;
    private int x_coordinate;

    public void saveDate(String tableName) {
        GameTime.operatingSql(new String[]
        {
        "update "+tableName+" set "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" "+Info.PRESTIGE+" = "+x_coordinate+" where "+Info.NAME+" = "+name
        });

    }


    public int getPlayerDate(String tableName) {
        Cursor iDate;
        iDate = GameTime.info.getWritableDatabase().rawQuery("select * from "+tableName,null);
        if (iDate == null) return -1;
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setMoney(iDate.getInt(iDate.getColumnIndex(Info.MONEY)));
        setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));
        if (name == null) return 1;
        return 0;
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

