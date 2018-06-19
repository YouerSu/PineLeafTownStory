package com.example.administrator.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Player{

    public static int money;
    public static int prestige;
    public static String name;
    public static int left;
    public static int deadline;
    //将来扩展异空间用


    public static void saveDate() {
        GameTime.operatingSql(new String[]
        {
        "update "+Info.PLAYER+" set "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" where "+Info.NAME+" = "+name
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


    public static int getPlayerDate() {
        Cursor iDate;
        iDate = GameTime.info.getWritableDatabase().rawQuery("select * from "+Info.PLAYER,null);
        if (iDate == null) return -1;
        name = iDate.getString(iDate.getColumnIndex(Info.NAME));
        money = iDate.getInt(iDate.getColumnIndex(Info.MONEY));
        prestige = iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE));
        left = iDate.getInt(iDate.getColumnIndex(Info.loan));
        deadline = iDate.getInt(iDate.getColumnIndex(Info.loanTime));
        if (name.equals("啦啦啦,我是没有名字的傻瓜")) return 1;
        return 0;
    }
}

