package com.example.administrator.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Sql extends SQLiteOpenHelper{
    private static final int VER = 1;
    private static SQLiteDatabase db;

    public Sql(Context context) {
        super(context, Info.INSTANCE.getName(),null, VER);
    }

    //用于执行Sql语句;
    public static void operating(String statements[]) {
        for (String sql:statements)
        getDateBase().execSQL(sql);
    }

    public static Cursor getAllInfo(String tableName) {
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
    ("create table if not exists "+ Info.INSTANCE.getCHARACTER() +"(" + Info.INSTANCE.getId() +" text," + Info.INSTANCE.getNAME() +" text,"+ Info.INSTANCE.getMONEY() +" integer,"+ Info.INSTANCE.getPRESTIGE() +" integer," + Info.INSTANCE.getCoordinate() +" integer,"+ Info.INSTANCE.getSalary() +" integer,"+ Info.INSTANCE.getMASTER() +" text)");
    db.execSQL
    ("create table if not exists "+ Info.INSTANCE.getTIME() +" ("+ Info.INSTANCE.getMINUTE() + " integer,"+ Info.INSTANCE.getHOUR() +" integer,"+ Info.INSTANCE.getDAY() +" integer,"+ Info.INSTANCE.getMONTH() +" integer,"+ Info.INSTANCE.getYEAR() + " integer)");
    db.execSQL
    ("insert into "+ Info.INSTANCE.getTIME() +" ("+ Info.INSTANCE.getMINUTE() +"," + Info.INSTANCE.getHOUR() +","+ Info.INSTANCE.getDAY() +"," + Info.INSTANCE.getMONTH() +","+ Info.INSTANCE.getYEAR() +") values (10,7,3,1,1)");
    db.execSQL
    ("create table if not exists "+ Info.INSTANCE.getBUILDING() +" (" + Info.INSTANCE.getNAME() +" text," + Info.INSTANCE.getMASTER() +" text,"+ Info.INSTANCE.getCapacity() +" integer)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void setInfo(Sql sql) {
        Sql info = sql;
        db = info.getWritableDatabase();
    }
}
