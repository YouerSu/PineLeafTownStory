package com.example.administrator.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Player<T>{

    public static int money;
    public static int prestige;
    public static String name;
    public static int left;
    public static int deadline;
    public static GameTime timeDate;
    //将来扩展多重空间坐标用
    private T coordinate;

    public Player(T coordinate) {
        this.coordinate = coordinate;
    }

    public void savePlayerDate() {
        GameTime.operatingSql(new String[]
        {
        "update "+Info.PLAYER+" set "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+
        " "+Info.MINUTE+" = "+timeDate.getMinute()+" "+Info.HOUR+" = "+timeDate.getHour()+
        " "+Info.DAY+" = "+timeDate.getDay()+" "+Info.MONTH+" = "+timeDate.getMonth()+
        " "+Info.YEAR+" = "+timeDate.getYear()+" "+Info.coordinate+" = "+coordinate+
        " where " +Info.NAME+" = "+name
        });
/*      SQLiteDatabase db = info.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Info.MONEY, money);
        values.put(Info.PRESTIGE, prestige);
        values.put(Info.YEAR, timeDate.getYear());
        values.put(Info.DAY, timeDate.getDay());
        values.put(Info.MONTH, timeDate.getMonth());
        values.put(Info.MINUTE, timeDate.getMinute());
        db.update(Info.PLAYER, values, Info.id + "=?", new String[]{"1"});
        db.close();                                                                 */

    }

    public T getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(T coordinate) {
        this.coordinate = coordinate;
    }
}

