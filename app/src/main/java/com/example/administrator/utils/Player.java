package com.example.administrator.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Player{

    public int money;
    public int prestige;
    public String name;
    public int totalAmountOwed;
    public int deadline;
    //将来扩展异空间用


    public void saveDate(String tableName) {
        GameTime.operatingSql(new String[]
        {
        "update "+tableName+" set "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" "+Info.loan+" = "+totalAmountOwed+" "+Info.loanTime+" = "+deadline+" where "+Info.NAME+" = "+name
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


    public int getPlayerDate(String tableName) {
        Cursor iDate;
        iDate = GameTime.info.getWritableDatabase().rawQuery("select * from "+tableName,null);
        if (iDate == null) return -1;
        name = iDate.getString(iDate.getColumnIndex(Info.NAME));
        money = iDate.getInt(iDate.getColumnIndex(Info.MONEY));
        prestige = iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE));
        totalAmountOwed = iDate.getInt(iDate.getColumnIndex(Info.loan));
        deadline = iDate.getInt(iDate.getColumnIndex(Info.loanTime));
        if (name.equals("啦啦啦,我是没有名字的傻瓜")) return 1;
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

    public int getTotalAmountOwed() {
        return totalAmountOwed;
    }

    public void setTotalAmountOwed(int totalAmountOwed) {
        this.totalAmountOwed = totalAmountOwed;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}

