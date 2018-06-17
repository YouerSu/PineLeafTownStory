package com.example.administrator.buildings;

public class SellItem extends Item{

    private int sellPrice;
    private int popular;

    public SellItem(String name, int volume, int originalPrice, int total, int sellPrice, int popular) {
        super(name, volume, originalPrice,total);
        this.sellPrice = sellPrice;
        this.popular = popular;
    }

    @Override
    public void AffectedByTheCurrentSituation() {

    }

    @Override
    public void purchase() {

    }

    @Override
    public void saveDate(String tableName) {

    }


}
