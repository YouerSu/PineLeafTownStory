package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.item.Item;
import com.example.administrator.item.SellItem;

import java.util.Random;

public class Customer extends Character implements NPC{

    @Override
    void initialization() {
        startActivity();
    }

    @Override
    public void work() {
        for (Item item: Building.buildings.get(getX_coordinate()).getItems().values()) {
            if (!(item instanceof SellItem)||((SellItem) item).getSellPrice()-((SellItem) item).getPopular()>item.getOriginalPrice()*1.5)
            continue;
            Random random = new Random();
            if (random.nextBoolean())
            Building.buildings.get(getX_coordinate()).work(item);
            else
            //大传送术 XD
            setX_coordinate(random.nextInt(Building.getBuildings().size()));
        }
    }
}
