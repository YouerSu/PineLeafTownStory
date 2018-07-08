package com.example.administrator.buildings;



import android.database.Cursor;

import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Building {
    private String name;
    private int capacity;
    private String master;
    private List<Employee> employees;
    private HashMap<String,Item> items;

    public Building(String name, int capacity, String master) {
        this.name = name;
        this.capacity = capacity;
        this.master = master;
    }

    //在GameTime类读取Building数据
    public void getDate(Cursor iDate) {
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setCapacity(iDate.getInt(iDate.getColumnIndex(Info.capacity)));
        setMaster(iDate.getString(iDate.getColumnIndex(Info.MASTER)));
        items = Item.getDate(name);
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
