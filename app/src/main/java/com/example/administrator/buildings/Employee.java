package com.example.administrator.buildings;



import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

import java.util.HashMap;

enum Career {StoresEmployee}

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

    public abstract void profitableEvent();

    public abstract void disasterEvent();

    public void saveDate(String tableName){
        GameTime.operatingSql(new String[]{
        "insert into "+tableName+"("+Info.id+","+Info.NAME+","+Info.salary+","+Info.LOYALTY+","+Info.ABILITY+","+Info.RISEPOTENTIAL+") values("+ career.ordinal()+","+name+","+salary+","+loyalty+","+ability+","+risePotential+")",
        });
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
}