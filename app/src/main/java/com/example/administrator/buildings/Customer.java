package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.NPC;
import com.example.administrator.utils.Player;

import java.util.Map;

public class Customer extends Player implements NPC {


    private int goodValue;


    @Override
    public Map<String, String> UIPageAdapter() {
        return GameTime.getAdapterMap(getName(),"好感值："+getGoodValue(),null,null);
    }

    @Override
    public void showMyOwnOnClick(GameUI UI, Building building) {

    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI, Building building) {

    }

    public void setGoodValue(int newGoodValue) {
        goodValue = newGoodValue;
    }

    public int getGoodValue() {
        return goodValue;
    }

    @Override
    public void work() {

    }

    @Override
    public void move() {

    }
}
