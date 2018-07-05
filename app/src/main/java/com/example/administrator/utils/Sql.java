package com.example.administrator.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.administrator.utils.Info.capacity;
import static com.example.administrator.utils.Info.customer;

public class Sql extends SQLiteOpenHelper{
    public Sql(Context context) {
        super(context,Info.name,null,Info.VER);
    }

    //我错了,不要打我(逃
    @Override
    public void onCreate(SQLiteDatabase db) {//□□□□□□
    db.execSQL("create table if not exists "+Info.PLAYER+"("
    +Info.NAME+" text not null,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer,"+Info.loan+
     " integer,"+Info.loanTime+" integer," +Info.coordinate+" integer)");db.execSQL//|工|工|工|工|
    ("create table if not exists "+Info.DIFFERENT_WORLD+"("+Info.NAME+","+Info.MINUTE+//|工|工|工|工|工|
        " integer,"+Info.HOUR+" integer,"+Info.DAY+" integer,"+Info.MONTH+" integer,"+Info.YEAR+ " integer)");
     //|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|
/**@@**/db.execSQL("insert into "+Info.PLAYER +"("+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + //|工|工|工|工|
/**@@**/","+Info.loan+"," +Info.loanTime+") values('啦啦啦,我是没有名字的傻瓜',0,0,10,0,0,1)");  /**@*///|工|工|
      //|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|
        db.execSQL("insert into "+Info.DIFFERENT_WORLD+"("+Info.NAME+","+Info.MINUTE+","/**@□□□□□@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
        +Info.HOUR +","+Info.DAY +"," +Info.MONTH +","+Info.YEAR +","+Info.coordinate+") values " +//|工|工|工-----------------
        "("+Info.PLACE_NAME+",10,7,3,1)");db.execSQL("create table if not exists "+Info.BUILDING+"("+Info.id+
                    " text," +Info.NAME +" text,"
        +Info.SECURITY+" integer,"+Info.FACILITIES +" integer,"+Info.capacity+" integer)");
//db.execSQL("insert into "+Info.STAFF_AND_SHOP +0+"("+Info.NAME +","+Info.CLEVER +","+Info.STRONG_LEVEL +","+Info.LOYALTY +","+Info.salary+","+Info.capacity+","+Info.customer+") values('建筑',100,20,2,0,550,0)");
//db.execSQL("create table if not exists "+Info.wareHouse+0+"("+Info.id+" integer primary key autoincrement,"+Info.NAME +" text,"+Info.sellPrice+" integer,"+Info.total+" integer)");
//db.execSQL("insert into "+Info.wareHouse+0+"("+Info.NAME +","+Info.sellPrice+","+Info.total+") values('杨梅',10,50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
