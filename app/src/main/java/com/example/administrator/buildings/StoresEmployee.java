package com.example.administrator.buildings;

import java.util.HashMap;
import java.util.List;

public class StoresEmployee extends Employee {

    public StoresEmployee(String name, int salary, int loyalty, int ability, int risePotential) {
        super(name, salary, loyalty, ability, risePotential, Career.StoresEmployee);
    }

    @Override
    public void profitableEvent() {

    }

    @Override
    public void disasterEvent() {

    }


}
