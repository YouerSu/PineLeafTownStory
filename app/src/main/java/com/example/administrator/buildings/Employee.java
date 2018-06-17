package com.example.administrator.buildings;


import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.UIAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Employee{
    private String name;
    private int salary;
    private int loyalty;
    private int ability;
    private int risePotential;

    public Employee(String name, int salary, int loyalty, int ability, int risePotential) {
        this.name = name;
        this.salary = salary;
        this.loyalty = loyalty;
        this.ability = ability;
        this.risePotential = risePotential;
    }

    public abstract void profitableEvent();

    public abstract void disasterEvent();

    public void saveDate(String name){

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
