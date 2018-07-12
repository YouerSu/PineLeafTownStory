package com.example.administrator.buildings;



public class StoresEmployee extends Employee{

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
