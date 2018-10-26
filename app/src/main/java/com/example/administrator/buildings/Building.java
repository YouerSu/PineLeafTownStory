package com.example.administrator.buildings;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.character.Character;
import com.example.administrator.character.Employee;
import com.example.administrator.item.Item;
import com.example.administrator.item.Tool;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Building implements OwnName {
    public static List<Building> buildings = new ArrayList<>();
    private String name;
    private int capacity;
    private String master;
    private HashMap<String,Item> items = new HashMap<>();
    private ArrayList<Employee> employees = new ArrayList<>();

    public Building(String name, int capacity, String master) {
        this.name = name;
        this.capacity = capacity;
        this.master = master;
        buildings.add(this);
    }

    public void addEmployee(Employee employee){
        employee.setMaster(name);
        employees.add(employee);
    }

    public static Building findWorkSpace(String workSpace){
        return Tools.findMaster(workSpace,buildings);
    }

    public Employee findWorker(Item item){
        for (Employee employee:employees)
            if (employee.receive(item)) return employee;
        return null;
    }

    public static void getBuildingDate() {
        Cursor iDate = Sql.getAllInfo(Info.BUILDING);
        if (iDate==null) return;
        while (iDate.moveToNext()){
            Building building = new Building(iDate.getString(iDate.getColumnIndex(Info.NAME)),iDate.getInt(iDate.getColumnIndex(Info.capacity)),iDate.getString(iDate.getColumnIndex(Info.MASTER)));
            building.items = Item.getIndexDate(building.getName());
        }
        iDate.close();
    }

    public static Building getWhere(int x){
        return buildings.get(x);
    }

    private void createNewBuilding(SQLiteDatabase db){
        Item.createIndex(name);
        db.execSQL
                ("insert into "+Info.BUILDING+" ("+Info.NAME+","+Info.MASTER+","+Info.capacity+") values ('"+name+"','"+master+"',"+capacity+")");
    }

    public void saveDate() {
        createNewBuilding(Sql.getDateBase());
        for (Item item : getItems().values())
            item.saveIndexDate(name);
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

    public List<Tool> services(){
        List<Tool> list = new ArrayList<>();
        for (Item item:items.values())
            if (item instanceof Tool)
                list.add((Tool)item);
        return list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
