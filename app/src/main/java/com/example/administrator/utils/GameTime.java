package com.example.administrator.utils;

import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Item;
import com.example.administrator.storeboss.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static com.example.administrator.utils.Info.capacity;

public class GameTime extends TimerTask {
    //数据
    public static int totalOfBuilding = 0;
    public static List<Building> cAndE = new ArrayList<>();
    public static String season = "春季日";
    public static Sql info;
    //如果有其他表示坐标方法就改类型(只要不是SQL存不了的就行),
    public static Player<Integer> coordinate;

    //各类时间事件
    public int hour;
    public int minute;
    public int day;
    public int month;
    public int year;

    public GameTime(int minute,int hour,int day,int month,int year) {
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
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
        //NPC的行为
        int count = -1;
    for (Building building: cAndE){
        count++;
        lalala:
        do {
        switch ((int) (Math.random() * 9)) {
            //提意见
            case 1:
                if (building.get(0).getClever()<120)
                continue;
                else {
                int totalOfClever = 0;
                for (Building clever:building){
                totalOfClever+=clever.getClever(); }
                if ((int)(Math.random()*totalOfClever)>70)
                building.get(0).setClever(1);}
                break;
            //离店
            case 2:
                if (building.get(0).getCustomer()>0){
                building.get(0).setCustomer(-1);
                if (building.get(0).getSalary()>0)
                building.get(0).setCapacity(+3);}
                if (Player.prestige>250)
                continue ;
                break;
            //购买
            case 3:
                int a =0;
                do{
                if (building.get(0).getCustomer()<=0|| allWare.get(count).size()<=0) continue lalala;
                a++;
                int b = (int)(Math.random()* allWare.get(count).size());
                Item ware = allWare.get(count).get(b);
                if (building.get(0).getSalary()==0){
                if (ware.getOriginalPrice()*2>ware.getSellPrice()-ware.getPopular()){
                Player.money +=ware.getSellPrice();
                allWare.get(count).get(b).setTotal(-1,count); } }
                else{
                Player.money +=ware.getSellPrice();
                building.get(0).setCustomer(-3);}
                }while (a<(Math.random()* Player.prestige)/4);
                if (Player.prestige>200)
                continue;
                break;
            //称赞
            case 4:
                if (Player.prestige<200){
                Player.prestige++;}
                else continue;
                break;
            //不满
            case 5:
                if (Player.prestige>0)
                Player.prestige--;
                continue;
            //进店
            default:
                if (building.get(0).getCustomer()<building.get(0).getCapacity()/3&&hour<22){
                int i = 0 ;
                for (Building allThings:building){
                i+=allThings.getClever(); }
                if ((int)(Math.random()*i)>8)
                building.get(0).setCustomer(1);
                if (building.get(0).getSalary()>0&&building.get(0).getCapacity()>3)
                building.get(0).setCapacity(-3);
                } } }while (false); }
                setTime(this);
    }

    public void setTime(GameTime timeDate){
        timeDate.setMinute(timeDate.getMinute()+2);
        if (timeDate.getMinute() >= 60) {
            timeDate.setMinute(0);
            timeDate.setHour(timeDate.getHour()+1);
            if (timeDate.getHour() >= 24) {
                timeDate.setHour(7);
                timeDate.setDay(timeDate.getDay()+1);
                if (timeDate.getDay() > 25) {
                    timeDate.setDay(1);
                    investment();
                    timeDate.setMonth(timeDate.getMonth()+1);
                    if (timeDate.getMonth()> 4) {
                        switch (timeDate.getMonth()) {
                            case 1:
                                season = "春季日";
                                break;
                            case 2:
                                season = "夏季日";
                                break;
                            case 3:
                                season = "秋季日";
                                break;
                            case 4:
                                season = "冬季日";
                        }
                        timeDate.setMonth(1);
                        timeDate.setYear(timeDate.getYear()+1);
                    }
                }
            }
        }
        Game.setTimeView();
    }

    public static boolean theft() {
    //偷窃
        int a = 0;
        for (List<Building>building: cAndE) {
            int i = 0;
            a++;
            for (Building allThings : building) {
                i += allThings.getStrongLevel();}
                if ((int) (Math.random() * i) < 5&&allWare.get(a).size()>0)
                    allWare.get(a).get((int)(Math.random()* allWare.get(a).size())).setTotal(-1,a);
                return true;
        }
        return false;
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

    public static void setCustomer() {
        for (List<Building>building: cAndE){
            building.get(0).setcustomer();
        }
    }
}
