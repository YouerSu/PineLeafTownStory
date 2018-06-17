package com.example.administrator.buildings;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;

public class SellItem extends Item{

    private int sellPrice;
    private int popular;

    public SellItem(String name, int volume, int originalPrice, int total, int sellPrice, int popular) {
        super(name, volume, originalPrice,total);
        this.sellPrice = sellPrice;
        this.popular = popular;
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
                "insert into "+tableName+" ("+ Info.sellPrice+") values ("+sellPrice+")"
        });
    }


}
