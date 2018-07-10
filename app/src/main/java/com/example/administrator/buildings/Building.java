package com.example.administrator.buildings;



import android.database.Cursor;

import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Building implements OwnName {
    public static List<Building> buildings = new ArrayList<>();
    private String name;
    private int capacity;
    private String master;
    private HashMap<String,Item> items;

    public Building(String name, int capacity, String master) {
        this.name = name;
        this.capacity = capacity;
        this.master = master;
        buildings.add(this);
    }

    static Building findWorkSpace(String workSpace){
        for (Building building:Character.findMaster(workSpace,buildings))
            return building;
        return null;
    }

    public String getMaster() {
        return master;
    }

    public static List<Building> getBuildings() {
        return buildings;
    }

    //在GameTime类读取Building数据
    public void getDate(Cursor iDate) {
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setCapacity(iDate.getInt(iDate.getColumnIndex(Info.capacity)));
        setMaster(iDate.getString(iDate.getColumnIndex(Info.MASTER)));
        items = Item.getSuperDate(name);
        buildings.add(this);
    }

    public void createBuilding() {
        Item.createTable(name);
        Sql.operatingSql(new String[]{
        "insert into " + Info.BUILDING + "(" + Info.NAME + "," + Info.capacity + ") values(" + name + "," + capacity + ")",
        });
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
        createBuilding();
        for (Item item : getItems())
            item.saveSuperDate(name + "Item");
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public static void clearSql(String tableName) {
        Sql.info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addItems(Item item) {
        this.items.put(item.getName(),item);
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void removeItem(Item item){
        if (item.getTotal()<=0)
        items.remove(item);
    }

}
