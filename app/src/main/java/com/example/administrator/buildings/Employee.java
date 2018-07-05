package com.example.administrator.buildings;

import android.database.Cursor;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//创建新的类型来这登记一下 XD
enum Career{StoresEmployee}

public abstract class Employee{
    private String name;
    private int salary;
    private int loyalty;
    private int ability;
    private int risePotential;
    private Career career;

    protected Employee(String name, int salary, int loyalty, int ability, int risePotential,Career career) {
        this.name = name;
        this.salary = salary;
        this.loyalty = loyalty;
        this.ability = ability;
        this.risePotential = risePotential;
        this.career = career;
    }

    public abstract boolean work(Item item);

    public abstract void setType();

    public abstract void disasterEvent();

    public abstract void saveDate(String tableName);

    public void wages(){
        GameTime.playerDate.setMoney(GameTime.playerDate.getMoney()-getSalary());
    }

    public void saveSuperDate(String tableName){
        GameTime.operatingSql(new String[]{
        "insert into "+tableName+"("+Info.id+","+Info.NAME+","+Info.salary+","+Info.LOYALTY+","+Info.ABILITY+","+Info.RISE_POTENTIAL +") values("+ getClass().getName()+","+name+","+salary+","+loyalty+","+ability+","+risePotential+")",
        });
        saveDate(tableName);
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public int getAbility() {
        return ability;
    }

    public int getRisePotential() {
        return risePotential;
    }

    public HashMap<String, String> UIPageAdapter() {
        HashMap<String,String> date = new HashMap<>();
        date.put("name",name);
        date.put("l1","薪酬:"+salary);
        date.put("l2","能力:"+ability);
        date.put("l3","好感:"+loyalty);
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public void setRisePotential(int risePotential) {
        this.risePotential = risePotential;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public static List<Employee> getDate(String tableName) {
        List<Employee> list = new ArrayList<>();
        Cursor iDate = GameTime.getCursor(tableName);
        while (iDate.moveToNext()){
            Employee employee = GameTime.getItem(iDate.getString(iDate.getColumnIndex(Info.id)));
            employee.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
            employee.setAbility(iDate.getInt(iDate.getColumnIndex(Info.ABILITY)));
            employee.setLoyalty(iDate.getInt(iDate.getColumnIndex(Info.LOYALTY)));
            employee.setSalary(iDate.getInt(iDate.getColumnIndex(Info.salary)));
            employee.setRisePotential(iDate.getInt(iDate.getColumnIndex(Info.RISE_POTENTIAL)));
            employee.setType();
            list.add(employee);
        }
        iDate.close();
        return list;
    }
}
