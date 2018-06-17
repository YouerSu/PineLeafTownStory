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

    @Override
    public void onCreate(SQLiteDatabase db) {//□□□□□□
    db.execSQL("create table if not exists "+Info.PLAYER+"("
        +Info.NAME+" text not null,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer,"+Info.MINUTE+
        " integer,"+Info.HOUR+" integer,"+Info.DAY+" integer,"+Info.MONTH+" integer,"+Info.YEAR+ " integer,"
        +Info.investment+" integer,"+Info.loan+" integer,"+Info.loanTime+" integer," +Info.coordinate+" integer)");
     //|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|
/**@@**/db.execSQL("insert into "+Info.PLAYER +"("+Info.id+","+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + //|工|工|
/**@@**/","+Info.MINUTE +","+Info.HOUR +","+Info.DAY +","+Info.MONTH +","+Info.YEAR +","+Info.loan+"," /**@*///|工|工|工|
     //|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|工|
        +Info.loanTime+","+Info.coordinate+") values(1,'啦啦啦,我是没有名字的傻瓜',0,0,10,10,7,3,1,0,0,1)");/**@□□□□□@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
        db.execSQL("create table if not exists "+Info.BUILDING+"("+Info.id+" text,"+Info.NAME +" text,"//|工|工|工-----------------
        +Info.SECURITY+" integer,"+Info.FACILITIES +" integer,"+Info.capacity+" integer,"+ customer+" integer)");
//db.execSQL("insert into "+Info.STAFF_AND_SHOP +0+"("+Info.NAME +","+Info.CLEVER +","+Info.STRONG_LEVEL +","+Info.LOYALTY +","+Info.salary+","+Info.capacity+","+Info.customer+") values('建筑',100,20,2,0,550,0)");
//db.execSQL("create table if not exists "+Info.wareHouse+0+"("+Info.id+" integer primary key autoincrement,"+Info.NAME +" text,"+Info.sellPrice+" integer,"+Info.total+" integer)");
//db.execSQL("insert into "+Info.wareHouse+0+"("+Info.NAME +","+Info.sellPrice+","+Info.total+") values('杨梅',10,50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
