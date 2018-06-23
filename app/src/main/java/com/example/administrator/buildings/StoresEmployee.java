package com.example.administrator.buildings;


import com.example.administrator.utils.GameTime;

public class StoresEmployee extends Employee {


    public StoresEmployee(String name, int salary, int loyalty, int ability, int risePotential) {
        super(name, salary, loyalty, ability, risePotential,Career.StoresEmployee);
    }

    @Override
    public boolean work(Item item) {
        //为了同种商品(或具有相似性质)能不同利用,所以放在员工这.
        if (item instanceof SellItem) return false;
        item.setTotal(item.getTotal()-1);
        GameTime.playerDate.setMoney(GameTime.playerDate.getMoney() + ((SellItem)item).getSellPrice());
        return true;
    }

    @Override
    public void disasterEvent() {

    }

    @Override
    public void saveDate(String tableName) {
    }
}
