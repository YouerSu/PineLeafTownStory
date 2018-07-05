package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Customer implements NPC {

    private String name;
    private int xCoordinate;
    private int money;
    private Article want;
    private int goodValue;

    public Customer(String name, int xCoordinate, int money, Article want, int goodValue) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.money = money;
        this.want = want;
        this.goodValue = goodValue;
    }

    @Override
    public void saveDate() {
        //改写XML文件
    }

    public void work(){
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getxCoordinate() {
        return xCoordinate;
    }

    @Override
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

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

    @Override
    public void setGoodValue(int newGoodValue) {
        goodValue = newGoodValue;
    }

    @Override
    public int getGoodValue() {
        return goodValue;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Article getWant() {
        return want;
    }

    public void setWant(Article want) {
        this.want = want;
    }



}
