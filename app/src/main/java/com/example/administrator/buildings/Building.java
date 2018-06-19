package com.example.administrator.buildings;



import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

import java.util.List;
public class Building/*<A extends Employee,B extends Item>*/{
    private String name;
    private int securityLevel;
    private int facilitiesLevel;
    private int capacity;
    private int customer;
    //protected List<A> employee;
    //protected List<B> markItem;
    //每次想用泛型都发现不合适,气死我啦!
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
    public void getDate(){
        employees = Employee.getDate(name+"Employee");
        items = Item.getDate(name+"Item");
    }

    private void createNewBuilding() {
        GameTime.operatingSql(new String[]{
        "insert into "+Info.BUILDING+"("+Info.NAME+","+Info.SECURITY+","+Info.FACILITIES+","+Info.capacity+","+Info.customer+") values("+name+","+securityLevel+","+facilitiesLevel+","+capacity+","+customer+")",
        "create table if not exists "+name+"Employee"+"("+Info.id +" integer,"+Info.NAME +" text," +Info.salary+" integer,"+Info.LOYALTY +" integer,"+Info.ABILITY+" integer,"+Info.RISEPOTENTIAL+" integer)",
        "create table if not exists "+name+"Item"+"("+Info.id +" integer,"+Info.NAME +" text,"+Info.total+" integer,"+Info.sellPrice+" integer)",
        "DELETE FROM "+name+"Employee",
        "DELETE FROM "+name+"Item"
        });
    }

    public String getName() {
        return name;
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

    public void saveDate(){
        createNewBuilding();
        for (Employee employee:employees)
            employee.saveDate(name+"Employee");
        for (Item item:items)
            item.saveDate(name+"Item");
    }



    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public static void clearSql(String tableName){
        GameTime.info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }
}
