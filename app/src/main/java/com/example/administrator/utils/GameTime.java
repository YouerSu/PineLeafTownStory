package com.example.administrator.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class GameTime extends TimerTask {

    //各类时间事件
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private GameUI gameUI;

    public <A extends GameUI>GameTime(A gameUI) {
        this.gameUI = gameUI;

    }

    public static<T> T getType(String className) {
        T article = null;
        try {
            Class type = Class.forName(className);
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


    public void saveDate(){
        Sql.operatingSql(new String[]{
        "update "+Info.DIFFERENT_WORLD+" set "+Info.MINUTE+" = "+getMinute()+" "+Info.HOUR+" = "+getHour()+ " "+Info.DAY+" = "+getDay()+" "+Info.MONTH+" = "+getMonth()+ " "+Info.YEAR+" = "+getYear()+" where "+Info.MINUTE+" = "+minute
        });
    }

    public boolean getTimeDate() {

        SQLiteDatabase data = Sql.info.getWritableDatabase();
        Cursor iDate = data.query(Info.DIFFERENT_WORLD, null, Info.NAME + "=?", new String[]{Info.PLACE_NAME}, null, null, null);
        setMinute(iDate.getInt(iDate.getColumnIndex(Info.MINUTE)));
        setHour(iDate.getInt(iDate.getColumnIndex(Info.HOUR)));
        setDay(iDate.getInt(iDate.getColumnIndex(Info.DAY)));
        setMonth(iDate.getInt(iDate.getColumnIndex(Info.MONTH)));
        setYear(iDate.getInt(iDate.getColumnIndex(Info.YEAR)));
        data.setTransactionSuccessful();
        data.endTransaction();
        return true;
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






}
