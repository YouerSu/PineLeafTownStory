package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.ShowAdapter;
import com.example.administrator.utils.Sql;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Character implements OwnName,ShowAdapter{
    public static LinkedList<Character> characters = new LinkedList<>();
    public int money;
    public int prestige;
    public String name;
    private static GameUI gameUI;
    private int x_coordinate;
    private int salary;
    private String workSpace;


    @Override
    public Map<String, String> UIPageAdapter() {
        return null;
    }

    abstract void initialization();

    @Override
    public void showMyOwnOnClick(GameUI UI, Building building) {

    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI, Building building) {

    }

    public static void getAllDate(GameUI gameUI){
        Cursor iDate = Sql.getCursorAllInformation(Info.BUILDING);
        while (iDate.moveToNext()){
            Building building = null;
            building.getDate(iDate);
            Building.getBuildings().add(building);
        }
        getDate(Sql.info.getWritableDatabase().rawQuery("select * from "+Info.CHARACTER,null));
    }

    public static void saveBuildingDate(){
        Sql.operatingSql(new String[]{"DELETE FROM "+Info.BUILDING});
        for (Building building: Building.getBuildings())
            building.saveDate();
    }


    public static void saveCharacterDate(String name){
        for (Character character:characters)
            character.saveDate();
    }


    public void saveDate(){
        Sql.operatingSql(new String[]{
        "update "+Info.CHARACTER+" set  "+Info.MONEY+" = "+money+" "+Info.PRESTIGE+" = "+prestige+" "+Info.PRESTIGE+" = "+x_coordinate+" "+Info.salary+" = "+salary+" "+Info.MASTER+" = "+ workSpace +" where "+Info.NAME+" = "+ name
        });
    }

    public static void getDate(Cursor iDate) {
        while (iDate.moveToNext()){
        Character character = GameTime.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
        character.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        character.setMoney(iDate.getInt(iDate.getColumnIndex(Info.MONEY)));
        character.setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));
        character.setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        character.setWorkSpace(iDate.getString(iDate.getColumnIndex(Info.MASTER)));
        character.setSalary(iDate.getInt(iDate.getColumnIndex(Info.salary)));
        character.setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        character.initialization();
        }
    }

    public void wages(){
        for (Character character:findMaster(Building.findWorkSpace(workSpace).getMaster(),characters))
            character.setMoney(character.getMoney()-getSalary());
        setMoney(getMoney()+getSalary());
    }

    public static<T extends OwnName> List<T> findMaster(String master,List<T> list) {
        List things = new ArrayList();
        for (T thing:list)
            if (master.equals(thing.getName())) things.add(thing);
        return things;
    }

    public static void findWorker(String buildingName,Item item){
        for (Character employee:findMaster(buildingName,characters))
            if (employee instanceof Employee&&((Employee) employee).work(item))
                return;

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

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
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

    public String getWorkSpace() {
        return workSpace;
    }
}


