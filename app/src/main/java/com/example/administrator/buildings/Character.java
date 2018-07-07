package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.NPC;
import com.example.administrator.utils.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Character extends Player implements NPC {
    private int salary;
    private int goodValue;
    private Player master;
    Career career;

    public void saveDate(String name){
        super.saveDate(name+Info.CHARACTER);
        GameTime.operatingSql(new String[]{
        "update "+name+Info.CHARACTER+Info.PLAYER+" set "+Info.salary+" = "+salary+" "+Info.GOOD_VALUE+" = "+goodValue+" "+Info.MASTER+" = "+master+" where "+Info.NAME+" = "+ this.name
        });
    }

    @Override
    public void getDate(Cursor iDate) {
        setMoney(iDate.getInt(iDate.getColumnIndex(Info.NAME)));
        setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        setPrestige(iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE)));
        setX_coordinate(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
        setGoodValue(iDate.getInt(iDate.getColumnIndex(Info.GOOD_VALUE)));
        setSalary(iDate.getInt(iDate.getColumnIndex(Info.salary)));
    }

    public void wages(){
        master.setMoney(master.getMoney()-getSalary());
        setMoney(getMoney()+getSalary());
    }

    @Override
    public void work() {
        career.behavior();
    }

    @Override
    public Map<String, String> UIPageAdapter() {
        HashMap<String,String> date = new HashMap<>();
        date.put("name",name);
        date.put("l1","薪酬:"+salary);
        date.put("l2","职业:"+career.getClass().getName());
        date.put("l3","好感:"+goodValue);
        return date;
    }

    @Override
    public void showMyOwnOnClick(GameUI UI, Building building) {

    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI, Building building) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getGoodValue() {
        return goodValue;
    }

    public void setGoodValue(int goodValue) {
        this.goodValue = goodValue;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
}
