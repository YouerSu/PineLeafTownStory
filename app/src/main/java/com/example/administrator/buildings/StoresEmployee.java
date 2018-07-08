package com.example.administrator.buildings;


public class StoresEmployee extends Employee{


    @Override
    public void behavior(Character character) {

    }

    @Override
    public boolean work(Item item,Player master) {
        if (item instanceof SellItem) return false;
        item.setTotal(item.getTotal()-1);
        master.setMoney(master.getMoney() + ((SellItem)item).getSellPrice());
        return true;
    }

}
