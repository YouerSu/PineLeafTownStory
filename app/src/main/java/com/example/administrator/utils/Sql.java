package com.example.administrator.utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.buildings.Building;
import com.example.administrator.character.Character;
import com.example.administrator.character.Employee;
import com.example.administrator.character.Player;
import com.example.administrator.character.StoresEmployee;
import com.example.administrator.item.Item;
import com.example.administrator.item.Mall;
import com.example.administrator.item.SellItem;

public class Sql extends SQLiteOpenHelper{
    //数据
    private static Sql info;
    private static SQLiteDatabase db;

    public Sql(Context context) {
        super(context,Info.name,null,Info.VER);
    }

    //用于执行Sql语句;
    public static void operatingSql(String statements[]) {
        for (String sql:statements)
        getDateBase().execSQL(sql);
    }

    public static Cursor getCursorAllInformation(String tableName) {
        return getDateBase().rawQuery("select * from "+tableName,null);
    }

    public static SQLiteDatabase getDateBase(){
        return db;
    }

    public static Cursor getCursor(String tableName, String want, String selectionArg, String[] selectionArgs){
        return getDateBase().rawQuery("select "+want+" from "+tableName+" WHERE "+selectionArg+" = ?",selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL
    ("create table if not exists "+Info.CHARACTER+"(" +Info.id+" text," +Info.NAME+" text,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer," +Info.coordinate+" integer,"+Info.salary+" integer,"+Info.MASTER+" text)");
    db.execSQL
    ("create table if not exists "+Info.TIME+" ("+Info.MINUTE+ " integer,"+Info.HOUR+" integer,"+Info.DAY+" integer,"+Info.MONTH+" integer,"+Info.YEAR+ " integer)");
    db.execSQL
    ("insert into "+Info.TIME+" ("+Info.MINUTE+"," +Info.HOUR +","+Info.DAY +"," +Info.MONTH +","+Info.YEAR +") values (10,7,3,1,1)");
    db.execSQL
    ("create table if not exists "+Info.BUILDING+" (" +Info.NAME +" text," +Info.MASTER +" text,"+Info.capacity+" integer)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void setInfo(Sql sql) {
        info = sql;
        db = info.getWritableDatabase();
    }
}
