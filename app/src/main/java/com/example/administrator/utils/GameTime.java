package com.example.administrator.utils;

import com.example.administrator.storeboss.Building;
import com.example.administrator.storeboss.WareHouse;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class GameTime extends TimerTask {
    //数据
    public static int totalOfBuilding = 0;
    public static int money = 0;
    public static int prestige;
    public static String name;
    public static List<List<Building>> cAndE = new ArrayList<>();
    public static List<List<WareHouse>> allWare = new ArrayList<>();

    //各类时间事件
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

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
    for (List<Building>building: cAndE){
        cout++;
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
                if (GameTime.prestige>250)
                continue ;
                break;
            //购买
            case 3:
                int a =0;
                do{
                if (building.get(0).getCustomer()<=0|| allWare.get(cout).size()<=0) continue lalala;
                a++;
                int b = (int)(Math.random()* allWare.get(cout).size());
                WareHouse ware = allWare.get(cout).get(b);
                if (building.get(0).getSalary()==0){
                if (ware.getoPrice()*2>ware.getSellPrice()-ware.getPopular()){
                money +=ware.getSellPrice();
                allWare.get(cout).get(b).setTotal(-1,cout); } }
                else{
                money +=ware.getSellPrice();
                building.get(0).setCustomer(-3);}
                }while (a<(Math.random()* prestige)/4);
                if (prestige>200)
                continue;
                break;
            //称赞
            case 4:
                if (prestige<200){
                prestige++;}
                else continue;
                break;
            //不满
            case 5:
                if (prestige>0)
                prestige--;
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
                } } }while (false); } }

    public static boolean theft() {
    //偷窃
        int a = 0;
        for (List<Building>building: cAndE) {
            int i = 0;
            a++;
            for (Building allThings : building) {
                i += allThings.getStrongLevel();}
                if ((int) (Math.random() * i) < 5)
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
        return ++hour;
    }

    public int getMinute() {
        minute+=10;
        return minute;
    }

    public int getDay() {
        return ++day;
    }

    public int getMonth() {
        return ++month;
    }

    public int getYear() {
        return ++year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear() {
        this.year++;
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
        for (List<Building>building: cAndE){
            building.get(0).setcustomer();
        }
    }
}
