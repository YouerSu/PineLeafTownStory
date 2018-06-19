package com.example.administrator.buildings;


public class StoresEmployee extends Employee {

    protected static final int storesEmployee = 0;

    public StoresEmployee(String name, int salary, int loyalty, int ability, int risePotential) {
        super(name, salary, loyalty, ability, risePotential,storesEmployee);
    }

    @Override
    public void profitableEvent() {

    }

    @Override
    public void disasterEvent() {

    }


}
