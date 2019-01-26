package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.buildings.Building;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

public class SellItem extends Item {
//TODO:将SellItem变为委托类
    private int sellPrice;

    public SellItem() {}

      @Override
    public void createTable(String workSpace){
        Sql.operating(new String[]{
        "create table if not exists "+workSpace+Tools.getSuffix(getClass().getName())+"("+ Info.INSTANCE.getNAME() +" text,"+ Info.INSTANCE.getSellPrice() +" integer)",
        "DELETE FROM " + workSpace + "SellItem"
        });
    }

    @Override
    public SellItem getListItem(String name) {
        if (name!=null) setName(name);
        return this;
    }

    public Item getItem(){
        return Building.findWorkSpace(getMaster()).getItems().get(super.getName());
    }

    @Override
    public boolean haveTable() {
        return true;
    }

    @Override
    public void setSQLDate(Cursor cursor) {
        sellPrice = cursor.getInt(cursor.getColumnIndex(Info.INSTANCE.getSellPrice()));
    }

    @Override
    public void saveDate(String name) {
        createTable(name);
        Sql.operating(new String[]{
                "insert into "+name+Tools.getSuffix(getClass().getName()) +" ("+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getSellPrice() +") values("+getName()+","+sellPrice+")"
        });
    }

//    @Override
//    public void showNotMyOwnOnClick(GameUI UI) {
//        String[] price = new String[1];
//        UI.reText("输入销售价格",price);
//        super.showNotMyOwnOnClick(UI);
//        new Response<String>(price){
//            @Override
//            public void doThings() {
//                setSellPrice(Integer.valueOf(price[0]));
//            }
//        };
//    }


    @Override
    public String getName() {
        return super.getName()+"(Sell)";
    }

    public synchronized int getSellPrice() {
        return sellPrice;
    }

}