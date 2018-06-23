package com.example.administrator.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Item;
import com.example.administrator.storeboss.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class GameTime<T> extends TimerTask {
    //数据
    public static int totalOfBuilding = 0;
    public static List<Building> buildings = new ArrayList<>();
    public static String season = "春季日";
    public static Sql info;
    public static Player playerDate;
    //各类时间事件
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    //如果有其他表示坐标方法就改类型(只要不是SQL存不了的就行),
    private T coordinate;
    private GameUI gameUI;

    public <A extends GameUI>GameTime(A gameUI) {
        this.gameUI = gameUI;
    }

    public static void getAllDate(){
        Cursor iDate = getCursor(Info.BUILDING);
        while (iDate.moveToNext()){
            Building building = new Building(iDate.getString(iDate.getColumnIndex(Info.NAME)),iDate.getInt(iDate.getColumnIndex(Info.SECURITY)),iDate.getInt(iDate.getColumnIndex(Info.FACILITIES)),iDate.getInt(iDate.getColumnIndex(Info.capacity)),iDate.getInt(iDate.getColumnIndex(Info.customer)));
            building.getDate();
            buildings.add(building);

        }
        getTimeDate(Info.PLACE_NAME);
        playerDate.getPlayerDate(Info.PLAYER);
    }

    public static void saveAllDate(GameTime timeDate){
        operatingSql(new String[]{"DELETE FROM "+Info.BUILDING});
        for (Building building:buildings)
        building.saveDate();
        playerDate.saveDate(Info.PLAYER);
        timeDate.saveDate();
    }

    public static Cursor getCursor(String tableName) {
        return info.getWritableDatabase().rawQuery("select * from "+tableName,null);
    }

    public void saveDate(){
        operatingSql(new String[]{
        "update "+Info.DIFFERENT_WORLD+" set "+Info.MINUTE+" = "+getMinute()+" "+Info.HOUR+" = "+getHour()+ " "+Info.DAY+" = "+getDay()+" "+Info.MONTH+" = "+getMonth()+ " "+Info.YEAR+" = "+getYear()+" "+Info.coordinate+" = "+coordinate+" where "+Info.MINUTE+/*异世界并不十分稳定(漏洞)*/" = "+minute
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
        setCoordinate((T) iDate.getString(iDate.getColumnIndex(Info.coordinate)));
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
        //NPC的行为,请允许我不创建实例了qwq,我(hao)怕(ma)会(fan)炸(de).
        int count = -1;
    for (Building building: buildings){
        building.work();
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
                gameUI.refreshUI();
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
        for (List<Building>building: buildings) {
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
        for (Building building:buildings)
            building.setCustomer(0);
    }

    public T getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(T coordinate) {
        this.coordinate = coordinate;
    }
}
