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
    public void onCreate(SQLiteDatabase db) {
    db.execSQL
    ("create table if not exists "+Info.PLAYER+"(" +Info.NAME+" text not null,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer,"+Info.loan+ " integer,"+Info.loanTime+" integer," +Info.coordinate+" integer)");
    db.execSQL
    ("create table if not exists "+Info.DIFFERENT_WORLD+"("+Info.NAME+" text,"+Info.MINUTE+ " integer,"+Info.HOUR+" integer,"+Info.DAY+" integer,"+Info.MONTH+" integer,"+Info.YEAR+ " integer)");
    db.execSQL
    ("insert into "+Info.PLAYER +"("+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + ") values(null,0,0)");
    db.execSQL  //世界名称,离开时时间
    ("insert into "+Info.DIFFERENT_WORLD+"("+Info.NAME+","+Info.MINUTE+"," +Info.HOUR +","+Info.DAY +"," +Info.MONTH +","+Info.YEAR +","+Info.coordinate+ ") " + "values " + "("+Info.PLACE_NAME+",10,7,3,1)");
    db.execSQL
    ("create table if not exists "+Info.BUILDING+"(" +Info.NAME +" text,"+Info.capacity+" integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
