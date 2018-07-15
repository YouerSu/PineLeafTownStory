package com.example.administrator.item;


import android.database.Cursor;

import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import java.util.HashMap;
import java.util.List;

public class Mall extends Item {

    private String itemList;

    public Mall(String name, int volume, int originalPrice, int total, String itemList) {
        super(name, volume, originalPrice, total);
        this.itemList = itemList;
    }

    public Mall(String name, int volume, int originalPrice, String itemList) {
        super(name, volume, originalPrice);
        this.itemList = itemList;
    }

    public Mall() {
    }

    @Override
    public void createItemTable(String name) {
    }

    @Override
    public Item[] getInfoDate(){
        return new Info.MALL().getItems();
//        for (Mall item: (Mall[]) new Info.MALL().getItems())
//            if (name.equals(item.getName()))
//                setItemList(item.getItemList());
    }

    @Override
    public void getSQLDate(Cursor cursor) {

    }

    @Override
    public void saveDate(String tableName) {

    }

    @Override
    public void getListDate(HashMap<String, Item> articles) {
        setItemList(((Mall)articles.get(getName())).getItemList());
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }


    @Override
    public void showOnClick(GameUI gameUI) {
    gameUI.showListDialogue((List<Item>)getAllItems(itemList).values());
    }


}
