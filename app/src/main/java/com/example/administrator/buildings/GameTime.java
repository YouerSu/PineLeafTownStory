package com.example.administrator.buildings;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

import java.util.TimerTask;

public class GameTime extends TimerTask {

    //各类时间事件
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private GameUI gameUI;


    public GameTime(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public static<T> T getType(String className) {
        T article = null;
        try {
            Class type = Class.forName(className);
            //newInstance回调用构造器
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




    public void saveDate(){
        Sql.operatingSql(new String[]{
        "update "+ Info.TIME+" set "+Info.MINUTE+" = "+getMinute()+","+Info.HOUR+" = "+getHour()+ ","+Info.DAY+" = "+getDay()+","+Info.MONTH+" = "+getMonth()+ ","+Info.YEAR+" = "+getYear()
        });
    }

    public boolean getTimeDate() {
        Cursor iDate = Sql.getCursorAllInformation(Info.TIME);
        if (iDate==null) return false;
        while (iDate.moveToNext()){
        //事实证明Cursor的指针是从第一条数据的前一个开始的
        setMinute(iDate.getInt(iDate.getColumnIndex(Info.MINUTE)));
        setHour(iDate.getInt(iDate.getColumnIndex(Info.HOUR)));
        setDay(iDate.getInt(iDate.getColumnIndex(Info.DAY)));
        setMonth(iDate.getInt(iDate.getColumnIndex(Info.MONTH)));
        setYear(iDate.getInt(iDate.getColumnIndex(Info.YEAR)));
        }
        return true;
    }


    @Override
    public void run() {
        setMinute(getMinute()+(int) Info.SPEED/1000);
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
