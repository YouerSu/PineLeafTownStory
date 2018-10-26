package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Tools;

import java.util.HashMap;

public class Mall extends Item {

    private Item[] list;

    public static Item[] items = new Mall[]{
            new Mall("SellItem", 0, 0, SellItem.items),
    };

    public Mall(String name, int volume, int originalPrice, Item[] itemList) {
        super(name, volume, originalPrice);
        setMaster("蚴牙");
        list = itemList;
    }

    public Mall() {
        super();
    }

    @Override
    public boolean haveTable() {
        return false;
    }

    @Override
    public void createTable(String name) {
    }

    @Override
    public Item[] getAllItems() {
        return items;
    }

    @Override
    public void getSQLDate(Cursor cursor) {
    }

    @Override
    public void saveDate(String tableName) {
    }

    @Override
    public void getListDate(HashMap<String, Item> articles) {
        setList(((Mall) articles.get(getName())).getList());
    }

    @Override
    public void showMyOwnOnClick(GameUI UI) {
        UI.showListDialogue(Tools.toList(list));
    }

    public Item[] getList() {
        return list;
    }

    public void setList(Item[] list) {
        this.list = list;
    }

}