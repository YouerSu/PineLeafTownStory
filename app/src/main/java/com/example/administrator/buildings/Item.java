package com.example.administrator.buildings;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.UIAdapter;

import java.util.HashMap;
import java.util.List;

public abstract class Item implements UIAdapter{
    String name;
    int volume;
    int originalPrice;
    int total;

    public Item(String name, int volume, int originalPrice, int total){
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
    }

    public abstract void affectedByTheCurrentSituation();

    public abstract void purchase();

    public void saveDate(String tableName){
    //我这个蒟蒻为了可扩展性牺牲了速度,
      GameTime.operatingSql(new String[]{
      "insert into "+tableName+" ("+Info.NAME+","+Info.total+") values ("+name+","+total+")"
        });
    }

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

    @Override
    public List<HashMap<String, String>> UIPageAdapter() {
        return null;
    }
}
