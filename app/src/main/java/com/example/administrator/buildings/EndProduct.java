package com.example.administrator.buildings;

public class EndProduct extends Item{

    private int sellPrice;
    private int popular;

    public EndProduct(String name, int volume, int originalPrice, int total, String xml, int sellPrice, int popular) {
        super(name, volume, originalPrice, total, xml);
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
    public void saveDate() {

    }


}
