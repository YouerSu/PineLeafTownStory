package com.example.administrator.buildings;



import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Building {
    private String name;
    private int securityLevel;
    private int facilitiesLevel;
    private int capacity;
    private List<Employee> employees;
    private HashMap<String,Item> items;

    public Building(String name, int securityLevel, int facilitiesLevel, int capacity) {
        this.name = name;
        this.securityLevel = securityLevel;
        this.facilitiesLevel = facilitiesLevel;
        this.capacity = capacity;
    }

    //在GameTime类读取Building数据
    public void getDate() {
        employees = Employee.getDate(name+"Employee");
        items = Item.getDate(name);
    }

    public void createBuilding() {
        Item.createTable(name);
        Employee.createTable(name);
        GameTime.operatingSql(new String[]{
        "insert into " + Info.BUILDING + "(" + Info.NAME + "," + Info.SECURITY + "," + Info.FACILITIES + "," + Info.capacity + "," + Info.customer + ") values(" + name + "," + securityLevel + "," + facilitiesLevel + "," + capacity + ")",
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public int getFacilitiesLevel() {
        return facilitiesLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void saveDate() {
        createBuilding();
        for (Employee employee : employees)
            employee.saveSuperDate(name + "Employee");
        for (Item item : getItems())
            item.saveSuperDate(name + "Item");
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public static void clearSql(String tableName) {
        GameTime.info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }

    public void work(Item item) {
        //找合适的人工作
        for (int i = 0; i < employees.size()||!employees.get(i).work(item);i++) ;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addEmployees(Employee employees) {
        this.employees.add(employees);
    }

    public void addItems(Item item) {
        this.items.put(item.getName(),item);
    }

    public void removeItem(Item item){
        if (item.getTotal()<=0)
        items.remove(item);
    }

}
