package com.example.administrator.item;


import android.database.Cursor;

import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Tools;

import java.util.HashMap;
import java.util.List;

public class Mall extends Item {

    public static Item[] items = new Mall[]{
    new Mall("SellItem",0,0,SellItem.items),
    };

    private Item[] list;



    public Mall(String name, int volume, int originalPrice, Item[] itemList) {
        super(name, volume, originalPrice);
        list = itemList;
    }

    public Mall() {}

    @Override
    public void createItemTable(String name) {}

    @Override
    public Item[] getAllDate(){
        return items;
    }

    @Override
    public void getSQLDate(Cursor cursor) {}

    @Override
    public void saveDate(String tableName) {}

    @Override
    public void getListDate(HashMap<String, Item> articles) {
        setList(((Mall)articles.get(getName())).getList());
    }

    public Item[] getList() {
        return list;
    }

    public void setList(Item[] list) {
        this.list = list;
    }

    @Override
    public void showMyOwnOnClick(GameUI UI) {
        UI.showListDialogue(Tools.toList(list));
    }
}
