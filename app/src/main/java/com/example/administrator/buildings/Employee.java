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

    public void saveDate(){

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

    public static List<HashMap<String, String>> UIPageAdapter(List<Employee> rawDate) {
        List<HashMap<String,String>> info = new ArrayList<>();
        for (int count = 0;count<rawDate.size();count++){
            HashMap<String,String> date = new HashMap<>();
            date.put("name",rawDate.get(count).name);
            date.put("l1","薪酬:"+rawDate.get(count).salary);
            date.put("l2","能力:"+rawDate.get(count).ability);
            date.put("l3","好感:"+rawDate.get(count).loyalty);
            info.add(date);
        }
        return info;
    }
}
