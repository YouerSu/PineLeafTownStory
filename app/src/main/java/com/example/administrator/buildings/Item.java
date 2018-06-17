package com.example.administrator.buildings;

public abstract class Item{
    String name;
    int volume;
    int originalPrice;
    int total;
    String xml;

    public Item(String name, int volume, int originalPrice, int total,String xml) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
        this.xml = xml;
    }

    public abstract void AffectedByTheCurrentSituation();

    public abstract void purchase();

    public void saveDate(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
