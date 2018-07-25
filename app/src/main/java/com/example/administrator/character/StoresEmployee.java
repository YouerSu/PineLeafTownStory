package com.example.administrator.character;


import com.example.administrator.item.Item;
import com.example.administrator.item.SellItem;
import com.example.administrator.buildings.Building;
import com.example.administrator.utils.Tools;

public class StoresEmployee extends Employee {

    @Override
    public boolean work(Item item) {
        if (!(item instanceof SellItem)) return false;
        item.setTotal(item.getTotal()-1);
        Building workSpace = Building.findWorkSpace(getMaster());
        Character master = Tools.findMaster(workSpace.getMaster(),getCharacters());
        master.setMoney(master.getMoney() + ((SellItem)item).getSellPrice());
        return true;
    }

}
