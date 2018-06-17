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
        GameTime.operatingSql(new String[]{
        "if exists (select * from "+Info.BUILDING+" where "+Info.NAME+"="+name+") begin update "+Info.BUILDING+" set "+Info.SECURITY+" = "+securityLevel+" "+Info.FACILITIES+" = "+facilitiesLevel+" "+Info.capacity+" = "+capacity+" "+Info.customer+" = "+customer+" where "+Info.NAME+" = "+name+" end else begin insert into "+Info.BUILDING+"("+Info.id+","+Info.name+","+Info.SECURITY+","+Info.FACILITIES+","+Info.capacity+","+Info.customer+") value("+this.getClass()+","+name+","+securityLevel+","+facilitiesLevel+","+capacity+","+customer+") end"
        });

    }

    public static void deleteSql(String tableName){
        GameTime.info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }
}
