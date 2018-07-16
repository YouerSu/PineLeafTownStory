package com.example.administrator.buildings;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.character.Character;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Building implements OwnName {
    public static List<Building> buildings = new ArrayList<>();
    private String name;
    private int capacity;
    private String master;
    private HashMap<String,Item> items = new HashMap<>();

    public Building(String name, int capacity, String master) {
        this.name = name;
        this.capacity = capacity;
        this.master = master;
        buildings.add(this);
    }

    public static Building findWorkSpace(String workSpace){
        for (Building building: Character.findMaster(workSpace,buildings))
            return building;
        return null;
    }

    public static void getBuildingDate() {
        Cursor iDate = Sql.getCursorAllInformation(Info.BUILDING);
        if (iDate!=null)
        while (iDate.moveToNext()){
            Building building = new Building(iDate.getString(iDate.getColumnIndex(Info.NAME)),iDate.getInt(iDate.getColumnIndex(Info.capacity)),iDate.getString(iDate.getColumnIndex(Info.MASTER)));
            building.items = Item.getSuperDate(building.getName());
        }
    }

    public String getMaster() {
        return master;
    }

    public static List<Building> getBuildings() {
        return buildings;
    }


    public String getName() {
        return name;
    }

    public void work(Item item){
        Character.findWorker(name,item);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void saveDate() {
        createNewBuilding(Sql.getDateBase());
        for (Item item : getItems().values())
            item.saveSuperDate(name);
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void createNewBuilding(SQLiteDatabase db){
        Item.createTable(name);
        db.execSQL
        ("insert into "+Info.BUILDING+" ("+Info.NAME+","+Info.MASTER+","+Info.capacity+") values ('"+name+"','"+master+"',"+capacity+")");
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaster(String master) {
        this.master = master;
    }


}
