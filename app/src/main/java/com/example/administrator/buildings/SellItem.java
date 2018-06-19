package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

import org.dom4j.Element;

import java.util.HashMap;

public class SellItem extends Item{

    private int sellPrice;
    private int popular;
    protected static final int sellItem = 0;

    public SellItem(String name, int volume, int originalPrice, int total, int sellPrice, int popular) {
        super(name, volume, originalPrice, total, sellItem);
        this.sellPrice = sellPrice;
        this.popular = popular;
    }

    public SellItem() {
        super();
    }

    @Override
    public void setType(HashMap<String,Item> articles) {
        popular = ((SellItem)articles.get(name)).getPopular();
    }

    @Override
    public void setType(Cursor cursor) {
        sellPrice = Integer.valueOf(cursor.getInt(cursor.getColumnIndex(Info.sellPrice)));
    }

    @Override
    public void setType(Element element) {
        this.popular = Integer.valueOf(element.elementText("popular"));
    }

    @Override
    public void affectedByTheCurrentSituation() {

    }

    @Override
    public void purchase() {

    }

    @Override
    public void saveDate(String tableName) {
        super.saveDate(tableName);
        GameTime.operatingSql(new String[]{
                "insert into "+tableName+" ("+ Info.sellPrice+") values("+sellPrice+")"
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
