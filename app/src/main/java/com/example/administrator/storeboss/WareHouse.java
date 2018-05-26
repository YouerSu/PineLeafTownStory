package com.example.administrator.storeboss;

public class WareHouse {

    String name;
    int volume;
    int oPrice;
    int sellPrice;
    int popular;
    int total;

    public WareHouse(String name, int volume, int oPrice, int sellPrice, int popular, int total) {
        this.name = name;
        this.volume = volume;
        this.oPrice = oPrice;
        this.sellPrice = sellPrice;
        this.popular = popular;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total,int who) {
        this.total += total;
        if (total<=0){
            Game.allWare.get(who).remove(this);
        }
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getoPrice() {
        return oPrice;
    }

    public void setoPrice(int oPrice) {
        this.oPrice = oPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
        }

    public int getPopular() {
        return popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }
}
