package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.item.Item;
import com.example.administrator.item.SellItem;

import java.util.Random;

public class Customer extends Character {

    @Override
    void init() {
        startActivity();
    }

    private boolean isExpensive(SellItem item) {
        int popular = item.getPopular();
        int value = item.getOriginalPrice();
        return item.getSellPrice()- popular>=value;
    }
}
