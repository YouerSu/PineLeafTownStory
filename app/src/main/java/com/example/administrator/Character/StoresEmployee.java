package com.example.administrator.Character;


import com.example.administrator.Item.Item;
import com.example.administrator.Item.SellItem;
import com.example.administrator.buildings.Building;

public class StoresEmployee extends Employee {

    @Override
    public boolean work(Item item) {
        if (!(item instanceof SellItem)) return false;
        item.setTotal(item.getTotal()-1);
        for (Character character:findMaster(Building.findWorkSpace(getWorkSpace()).getMaster(),characters))
            if (character!=null)
            character.setMoney(character.getMoney() + ((SellItem)item).getSellPrice());
        return true;
    }

}
