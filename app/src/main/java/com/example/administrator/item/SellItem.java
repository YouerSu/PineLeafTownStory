package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.HashMap;

public class SellItem extends Item {

    private int sellPrice;
    private int popular;
    public static Item[] items = new SellItem[]{
    new SellItem("杨梅",2,6,10),
    };

    public SellItem(String name, int volume, int originalPrice, int popular) {
        super(name, volume, originalPrice);
        this.popular = popular;
    }

    public SellItem() {
    }

    @Override
    public Item[] getAllItems() { return items; }

      @Override
    public void createTable(String name){
        Sql.operating(new String[]{
        "create table if not exists "+name+getClass().getName().substring(getClass().getName().lastIndexOf(".")+1)+"("+Info.NAME+" text,"+Info.sellPrice+" integer)",
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
    public void saveDate(String name) {
        createTable(name);
        Sql.operating(new String[]{
                "insert into "+name+Tools.getSuffix(getClass().getName()) +" ("+ Info.NAME+","+ Info.sellPrice+") values("+getName()+","+sellPrice+")"
        });
    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI) {
        String[] price = new String[1];
        UI.reText("输入销售价格",price);
        super.showNotMyOwnOnClick(UI);
        new Response<String>(price){
            @Override
            public void doThings() {
                setSellPrice(Integer.valueOf(price[0]));
            }
        };
    }

    public synchronized int getSellPrice() {
        return sellPrice;
    }

    public int getPopular() {
        return popular;
    }

    public synchronized void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }
}
