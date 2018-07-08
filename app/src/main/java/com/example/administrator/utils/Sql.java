package com.example.administrator.utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.administrator.utils.Info.capacity;
import static com.example.administrator.utils.Info.customer;

public class Sql extends SQLiteOpenHelper{
    //数据
    public static Sql info;

    public Sql(Context context) {
        super(context,Info.name,null,Info.VER);
    }

    //用于执行Sql语句;
    public static void operatingSql(String statements[]) {
        SQLiteDatabase db = info.getWritableDatabase();
        db.beginTransaction();
        for (int count = 0;count<statements.length;count++)
        db.execSQL(statements[count]);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public static Cursor getCursorAllInformation(String tableName) {
        return info.getWritableDatabase().rawQuery("select * from "+tableName,null);
    }

    public static Cursor getCursor(String tableName, String want, String selectionArg, String[] selectionArgs){
        return info.getWritableDatabase().rawQuery("select "+want+" from "+tableName+" WHERE "+selectionArg+" = ?",selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//    db.execSQL
//    ("create table if not exists "+Info.PLAYER+"(" +Info.NAME+" text not null,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer," +Info.coordinate+" integer)");
    db.execSQL
    ("create table if not exists "+Info.CHARACTER+"(" +Info.NAME+" text not null,"+Info.MONEY+" integer,"+Info.PRESTIGE+" integer," +Info.coordinate+" integer,"+Info.salary+" integer,"+Info.MASTER+" text)");
    db.execSQL
    ("create table if not exists "+Info.DIFFERENT_WORLD+"("+Info.NAME+" text,"+Info.MINUTE+ " integer,"+Info.HOUR+" integer,"+Info.DAY+" integer,"+Info.MONTH+" integer,"+Info.YEAR+ " integer)");
    db.execSQL
    ("insert into "+Info.PLAYER +"("+Info.NAME +","+Info.MONEY +","+Info.PRESTIGE + ","+Info.coordinate+ ") values(null,0,0,0)");
    db.execSQL
    ("insert into "+Info.DIFFERENT_WORLD+"("+Info.NAME+","+Info.MINUTE+"," +Info.HOUR +","+Info.DAY +"," +Info.MONTH +","+Info.YEAR +") " + "values " + "("+Info.PLACE_NAME+",10,7,3)");
    db.execSQL
    ("create table if not exists "+Info.BUILDING+"(" +Info.NAME +" text," +Info.MASTER +" text,"+Info.capacity+" integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
