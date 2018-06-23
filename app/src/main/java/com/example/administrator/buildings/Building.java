package com.example.administrator.buildings;



import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;

import java.util.List;

public class Building implements OwnName{
    private String name;
    private int securityLevel;
    private int facilitiesLevel;
    private int capacity;
    private int customer;
    private List<Employee> employees;
    private List<Item> items;

    public Building(String name, int securityLevel, int facilitiesLevel, int capacity, int customer) {
        this.name = name;
        this.securityLevel = securityLevel;
        this.facilitiesLevel = facilitiesLevel;
        this.capacity = capacity;
        this.customer = customer;
    }

    //在GameTime类读取Building数据
    public void getDate() {
        employees = Employee.getDate(name + "Employee");
        items = Item.getDate(name + "Item");
    }

    private void createNewBuilding() {
        GameTime.operatingSql(new String[]{
                "insert into " + Info.BUILDING + "(" + Info.NAME + "," + Info.SECURITY + "," + Info.FACILITIES + "," + Info.capacity + "," + Info.customer + ") values(" + name + "," + securityLevel + "," + facilitiesLevel + "," + capacity + "," + customer + ")",
                "create table if not exists " + name + "Employee" + "(" + Info.id + " text," + Info.NAME + " text," + Info.salary + " integer," + Info.LOYALTY + " integer," + Info.ABILITY + " integer," + Info.RISEPOTENTIAL + " integer)",
                "create table if not exists " + name + "Item" + "(" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer," + Info.sellPrice + " integer)",
                "DELETE FROM " + name + "Employee",
                "DELETE FROM " + name + "Item"
        });
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
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

    public int getCustomer() {
        return customer;
    }

    public void saveDate() {
        createNewBuilding();
        for (Employee employee : employees)
            employee.saveSuperDate(name + "Employee");
        for (Item item : items)
            item.saveSuperDate(name + "Item");
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public static void clearSql(String tableName) {
        GameTime.info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }

    public void work(Item item) {
        //找合适的人工作
        for (int i = 0; i < employees.size()||!employees.get(i).work(item);i++) ;
    }
}
