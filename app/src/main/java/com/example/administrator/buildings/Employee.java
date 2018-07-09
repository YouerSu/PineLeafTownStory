package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;

public abstract class Employee extends Character{


    abstract boolean work(Item item);

    @Override
    void initialization() {

    }
}
