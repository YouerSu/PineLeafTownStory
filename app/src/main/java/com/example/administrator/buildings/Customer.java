package com.example.administrator.buildings;

import com.example.administrator.utils.NPC;

import java.util.Random;

public class Customer extends Character implements NPC{

    @Override
    void initialization() {
        startActivity();
    }

    @Override
    public void work() {
        for (Item item:Building.buildings.get(getX_coordinate()).getItems()) {
            if (!(item instanceof SellItem)||((SellItem) item).getSellPrice()-((SellItem) item).getPopular()>item.getOriginalPrice()*1.5)
            continue;
            Random random = new Random();
            if (random.nextBoolean())
            Building.buildings.get(getX_coordinate()).work(item);
        }
    }
}
