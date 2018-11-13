package com.example.administrator.item;

import android.database.Cursor;

public class Mall extends Item {

    private Item[] list;

    public static Mall[] items = new Mall[]{

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
    public void createTable(String name) { }

    @Override
    public void setSQLDate(Cursor cursor) { }

    @Override
    public void saveDate(String tableName) { }

    @Override
    public Mall getListItem(String name) {
        return changeToMap(items).get(name);
    }

    public Item[] getList() {
        return list;
    }

    public void setList(Item[] list) {
        this.list = list;
    }

}