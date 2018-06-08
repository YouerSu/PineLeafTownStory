package com.example.administrator.storeboss;

import com.example.administrator.utils.GameTime;

import java.util.List;

public class WareHouse {

    String name;
    int volume;
    int oPrice;
    int sellPrice;
    int popular;
    int total;
    int whenPopular;

    public WareHouse(String name, int volume, int oPrice, int sellPrice, int popular, int total,int whenPopular) {
        this.name = name;
        this.volume = volume;
        this.oPrice = oPrice;
        this.sellPrice = sellPrice;
        this.popular = popular;
        this.total = total;
        this.whenPopular = whenPopular;
    }

    public WareHouse(int volume, int oPrice,int popular,int whenPopular) {
        this.volume = volume;
        this.oPrice = oPrice;
        this.popular = popular;
        this.whenPopular = whenPopular;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total,int who) {
        this.total += total;
        if (this.total<=0){
            GameTime.allWare.get(who).remove(this);
        }
    }

    public static int getAllVolume(List<WareHouse> allVolume){
        int i = 0;
        for (WareHouse totalVolume:allVolume)
            i+=totalVolume.getTotalVolume();
        return i;
    }

    public int getTotalVolume(){
        return total*volume;
    }

    public int getWhenPopular() {
        return whenPopular;
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
