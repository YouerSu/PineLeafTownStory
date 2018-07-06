package com.example.administrator.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.administrator.buildings.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class GameTime extends TimerTask {
    //数据
    public static Sql info;
    public static Player playerDate;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();

    //各类时间事件
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private GameUI gameUI;

    public <A extends GameUI>GameTime(A gameUI) {
        this.gameUI = gameUI;
       //Customer.randomList(customers,buildings.size(),playerDate.getPrestige());
    }

    public static<T> T getItem(String articles) {
        T article = null;
        try {
            Class type = Class.forName(articles);
            article = (T) type.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return article;
    }

    @NonNull
    public static Map<String, String> getAdapterMap(String name, String l1, String l2, String l3) {
        Map<String,String> item = new HashMap<>();
        item.put(Info.NAME,name);
        item.put(Info.LT1,l1);
        item.put(Info.LT2,l2);
        item.put(Info.LT3,l3);
        return item;
    }

    public void getAllDate(){
        Cursor iDate = getCursorAllInformation(Info.BUILDING);
        while (iDate.moveToNext()){
            Building building = new Building(iDate.getString(iDate.getColumnIndex(Info.NAME)),iDate.getInt(iDate.getColumnIndex(Info.SECURITY)),iDate.getInt(iDate.getColumnIndex(Info.FACILITIES)),iDate.getInt(iDate.getColumnIndex(Info.capacity)));
            building.getDate();
            buildings.add(building);

        }
        getTimeDate(Info.PLACE_NAME);
        playerDate.getPlayerDate(Info.PLAYER);
    }

    public void saveAllDate(GameTime timeDate){
        operatingSql(new String[]{"DELETE FROM "+Info.BUILDING});
        for (Building building:buildings)
        building.saveDate();
        playerDate.saveDate(Info.PLAYER);
        timeDate.saveDate();
    }

    public static Cursor getCursorAllInformation(String tableName) {
        return info.getWritableDatabase().rawQuery("select * from "+tableName,null);
    }

    public static Cursor getCursor(String tableName,String want,String selectionArg,String[] selectionArgs){
        return info.getWritableDatabase().rawQuery("select "+want+" from "+tableName+" WHERE "+selectionArg+" = ?",selectionArgs);
    }

    public void saveDate(){
        operatingSql(new String[]{
        "update "+Info.DIFFERENT_WORLD+" set "+Info.MINUTE+" = "+getMinute()+" "+Info.HOUR+" = "+getHour()+ " "+Info.DAY+" = "+getDay()+" "+Info.MONTH+" = "+getMonth()+ " "+Info.YEAR+" = "+getYear()+" "+Info.coordinate+" = "+ playerDate.getX_coordinate() +" where "+Info.MINUTE+/*异世界并不十分稳定(漏洞)*/" = "+minute
        });
    }

    public boolean getTimeDate(String placeName) {
        SQLiteDatabase data = GameTime.info.getWritableDatabase();
        Cursor iDate = data.query(Info.DIFFERENT_WORLD, null, Info.NAME + "=?", new String[]{placeName}, null, null, null);
        setMinute(iDate.getInt(iDate.getColumnIndex(Info.MINUTE)));
        setHour(iDate.getInt(iDate.getColumnIndex(Info.HOUR)));
        setDay(iDate.getInt(iDate.getColumnIndex(Info.DAY)));
        setMonth(iDate.getInt(iDate.getColumnIndex(Info.MONTH)));
        setYear(iDate.getInt(iDate.getColumnIndex(Info.YEAR)));
        playerDate.setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        data.setTransactionSuccessful();
        data.endTransaction();
        return true;
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


    @Override
    public void run() {

        setMinute(getMinute()+2);
        if (getMinute() >= 60) {
            setMinute(0);
            setHour(getHour()+1);
            if (getHour() >= 24) {
                setHour(7);
                setDay(getDay()+1);
                if (getDay() > 25) {
                    setDay(1);
                    investment();
                    setMonth(getMonth()+1);
                    if (getMonth()> 4) {
                        setMonth(1);
                        setYear(getYear()+1);
                    }
                }
            }
        }
                gameUI.refreshUI();
    }

    public void setTime(){

    }



    public static void investment(){
    //投资
    }

    public static void politicalChanges(){
    //时局变化
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }





    public List<Building> getBuildings() {
        return buildings;
    }
}
