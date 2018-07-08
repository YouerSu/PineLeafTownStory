package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.NPC;
import com.example.administrator.utils.Sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Character{
    public static LinkedList<Character> characters;
    public int money;
    public int prestige;
    public String name;
    private GameUI gameUI;
    private int x_coordinate;
    private int salary;
    private List<Building> buildings = new ArrayList<>();
    private String master;
//    Career career;


    public Character(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public void getAllDate(){
        Cursor iDate = Sql.getCursorAllInformation(Info.BUILDING);
        while (iDate.moveToNext()){
            Building building = null;
            building.getDate(iDate);
            buildings.add(building);
        }
        getDate(Sql.info.getWritableDatabase().rawQuery("select * from "+Info.PLAYER,null));
    }

    public void saveAllDate(){
        Sql.operatingSql(new String[]{"DELETE FROM "+Info.BUILDING});
        for (Building building:buildings)
            building.saveDate();
    }

    public static void saveCharacterDate(String name){
        for (Character character:characters)
            character.saveDate();
    }


    public void saveDate(){
        Sql.operatingSql(new String[]{
        "update "+Info.CHARACTER+" set  "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" "+Info.PRESTIGE+" = "+x_coordinate+" "+Info.salary+" = "+salary+" "+Info.MASTER+" = "+master+" where "+Info.NAME+" = "+ name
        });
    }

    public void getDate(Cursor iDate) {
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setMoney(iDate.getInt(iDate.getColumnIndex(Info.MONEY)));
        setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));
        setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        setMaster(iDate.getString(iDate.getColumnIndex(Info.MASTER)));
        setSalary(iDate.getInt(iDate.getColumnIndex(Info.salary)));
        setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
    }

    public void wages(){
        findMaster(master).setMoney(findMaster(master).getMoney()-getSalary());
        setMoney(getMoney()+getSalary());
    }

    private static Character findMaster(String master) {
        for (Character character:characters)
            if (master.equals(character.getName()))
                return character;
        return null;
    }

//    @Override
//    public void work() {
//        career.behavior(this);
//    }

////    @Override
////    public Map<String, String> UIPageAdapter() {
////        HashMap<String,String> date = new HashMap<>();
////        date.put("name",name);
////        date.put("l1","薪酬:"+salary);
////        date.put("l2","职业:"+career.getClass().getName());
//        return date;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


//    public Career getCareer() {
//        return career;
//    }
//
//    public void setCareer(Career career) {
//        this.career = career;
//    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }


    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public static LinkedList<Character> getCharacters() {
        return characters;
    }

    public static void setCharacters(LinkedList<Character> characters) {
        Character.characters = characters;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public String getMaster() {
        return master;
    }
}


