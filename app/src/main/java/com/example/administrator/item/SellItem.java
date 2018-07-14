package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;

import org.dom4j.Element;

import java.util.HashMap;

public class SellItem extends Item {

    private int sellPrice;
    private int popular;

    public SellItem(String name, int volume, int originalPrice, int total, int sellPrice, int popular) {
        super(name, volume, originalPrice, total);
        this.sellPrice = sellPrice;
        this.popular = popular;
    }

    public SellItem() {
    }

    @Override
    public void createItemTable(String name){
        Sql.operatingSql(new String[]{
        "create table if not exits "+name+getClass().getName().substring(getClass().getName().lastIndexOf(".")+1)+"("+Info.NAME+" text,"+Info.sellPrice+" integer)",
        "DELETE FROM " + name + "SellItem"
        });
    }


    @Override
    public void getListDate(HashMap<String,Item> articles) {
        popular = ((SellItem)articles.get(getName())).getPopular();
    }

    @Override
    public void getSQLDate(Cursor cursor) {
        sellPrice = cursor.getInt(cursor.getColumnIndex(Info.sellPrice));
    }

    @Override
    public void getXMLDate(Element element) {
        this.popular = Integer.valueOf(element.elementText("popular"));
    }


    @Override
    public void saveDate(String name) {
        createItemTable(name);
        Sql.operatingSql(new String[]{
        "insert into "+name+getClass().getName().substring(getClass().getName().lastIndexOf(".")+1)+" ("+ Info.NAME+","+ Info.sellPrice+") values("+getName()+","+sellPrice+")"
        });
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getPopular() {
        return popular;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }
}
