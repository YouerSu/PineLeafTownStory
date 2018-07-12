package com.example.administrator.Item;

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

    public static void createSellItem(String name){
        Sql.operatingSql(new String[]{
        "create table if not exits "+name+SellItem.class.getName()+"("+Info.NAME+" text,"+Info.sellPrice+" integer)",
        "DELETE FROM " + name + "SellItem"
        });
    }


    @Override
    public void getDate(HashMap<String,Item> articles) {
        popular = ((SellItem)articles.get(getName())).getPopular();
    }

    @Override
    public void getDate(String name,String ItemName) {
        Cursor cursor = Sql.getCursor(name+SellItem.class.getName(),Info.sellPrice,Info.NAME,new String[]{getName()});
        sellPrice = Integer.valueOf(cursor.getInt(cursor.getColumnIndex(Info.sellPrice)));
    }

    @Override
    public void getXMLDate(Element element) {
        this.popular = Integer.valueOf(element.elementText("popular"));
    }

    @Override
    public void purchase() {

    }

    @Override
    public void saveDate(String name) {
        createSellItem(name);
        Sql.operatingSql(new String[]{
        "insert into "+name+SellItem.class.getName()+" ("+ Info.NAME+","+ Info.sellPrice+") values("+getName()+","+sellPrice+")"
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
