package com.example.administrator.utils;

import com.example.administrator.storeboss.Building;
import com.example.administrator.storeboss.Game;
import com.example.administrator.storeboss.WareHouse;

import java.util.List;
import java.util.TimerTask;

public class GameTime extends TimerTask {
//各类时间事件

    private static int hour = 0;
    private static int minute = 0;
    private static int day=1;
    private static int month=1;
    private static int year=1;

    public GameTime(int minute,int hour,int day,int month,int year) {
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public void run() {
        //NPC的行为
        int cout = -1;
    for (List<Building>building:Game.cAndE){
            cout++;
        do {
            switch ((int) (Math.random() * 10)) {
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
                    if (building.get(0).getCustomer()>0)
                        building.get(0).setCustomer(-1);
                    break;
                //购买
                case 3:
                        if (building.get(0).getCustomer()>0&&Game.allWare.get(cout).size()>0){
                        WareHouse ware = Game.allWare.get(cout).get((int)(Math.random()*Game.allWare.get(cout).size()));
                        if (building.get(0).getCustomer()>0){
                            if (building.get(0).getSalary()==0){
                                    if (ware.getoPrice()*2>ware.getSellPrice()-ware.getPopular()){
                                        Game.money +=ware.getSellPrice();
                                        ware.setTotal(-1,cout);
                                    } }
                            else{
                                Game.money +=ware.getSellPrice();
                                ware.setTotal(-1,cout);
                                building.get(0).setCustomer(-1);}
                            if (Game.prestige>200)
                                continue;
                        }
                        }

                    break;
                //称赞
                case 4:
                    if (Game.prestige<200)
                        Game.prestige++;
                    else continue;
                    break;
                //不满
                case 5:
                    Game.prestige--;
                    continue;
                //进店
                default:
                    if (building.get(0).getCustomer()<building.get(0).getCapacity()){
                        int i = 0 ;
                        for (Building allthings:building){
                            i+=allthings.getClever();
                        }
                        if ((int)(Math.random()*i)>building.get(0).getClever()-5)
                            building.get(0).setCustomer(1);
                    }

            }
        }while (false);
         }}

    public static boolean theft() {
    //偷窃
        for (List<Building>building:Game.cAndE) {
            int i = 0;
            for (Building allthings : building) {
                i += allthings.getStrongLevel();
                if ((int) (Math.random() * i) < 5)
                        building.get(0).setCustomer(1);
            }
        }
        return false;
    }

    public static void investment(){
    //投资
    }

    public static void politicalChanges(){
    //时局变化
    }



    public static int getHour() {
        return ++hour;
    }

    public static int getMinute() {
        minute+=10;
        return minute;
    }

    public static int getDay() {
        return ++day;
    }

    public static int getMonth() {
        return ++month;
    }

    public static int getYear() {
        return ++year;
    }

    public static void setHour(int hour) {
        GameTime.hour = hour;
    }

    public static void setMinute(int minute) {
        GameTime.minute = minute;
    }

    public static void setDay(int day) {
        GameTime.day = day;
    }

    public static void setMonth(int month) {
        GameTime.month = month;
    }

    public static void setYear() {
        GameTime.year++;
    }

    public int getmonth() {
        return month;
    }

    public int getyear() {
        return year;
    }

    public int gethour() {
        return hour;
    }

    public int getday() {
        return day;
    }

    public int getminute() {
        return minute;
    }

    public static void setCustomer() {
        for (List<Building>building:Game.cAndE){
            building.get(0).setcustomer();
        }
    }
}
