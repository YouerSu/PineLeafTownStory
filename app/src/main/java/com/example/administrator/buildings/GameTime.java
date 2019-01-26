package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.character.Character;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

import java.util.Timer;
import java.util.TimerTask;

public class GameTime extends TimerTask {

    public static GameTime timeDate;
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

    public static void setTimeDate(GameUI gameUI) {
        timeDate = new GameTime(gameUI);
        timeDate.getTimeDate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                gameUI.run(timeDate);
            }
        }, 800L, Info.INSTANCE.getSPEED());
    }


    public void saveDate(){
        Sql.operating(new String[]{
        "update "+ Info.INSTANCE.getTIME() +" set "+ Info.INSTANCE.getMINUTE() +" = "+getMinute()+","+ Info.INSTANCE.getHOUR() +" = "+getHour()+ ","+ Info.INSTANCE.getDAY() +" = "+getDay()+","+ Info.INSTANCE.getMONTH() +" = "+getMonth()+ ","+ Info.INSTANCE.getYEAR() +" = "+getYear()
        });
    }

    public void getTimeDate() {
        Cursor iDate = Sql.getAllInfo(Info.INSTANCE.getTIME());
        if (iDate!=null)
        while (iDate.moveToNext()){
        //事实证明Cursor的指针是从第一条数据的前一个开始的
        setMinute(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getMINUTE())));
        setHour(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getHOUR())));
        setDay(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getDAY())));
        setMonth(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getMONTH())));
        setYear(iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getYEAR())));
        }
    }


    @Override
    public void run() {
        setMinute(getMinute()+(int) Info.INSTANCE.getSPEED() /1000);
        if (getMinute() >= 60) {
            setMinute(0);
            setHour(getHour()+1);
            if (getHour() >= 24) {
                setHour(7);
                setDay(getDay()+1);
                gameUI.dayChange();
                if (getDay() > 25) {
                    setDay(1);
                    setMonth(getMonth()+1);
                    for (Character character:Character.getCharacters())
                        character.wages();
                    if (getMonth()> 4) {
                        setMonth(1);
                        setYear(getYear()+1);
                    }
                }
            }
        }
        gameUI.refreshUI();
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