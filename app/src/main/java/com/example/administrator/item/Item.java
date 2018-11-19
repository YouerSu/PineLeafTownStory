package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.buildings.Building;
import com.example.administrator.listener.Click;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.OwnMaster;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.HashMap;
import java.util.Map;


public abstract class Item implements ShowAdapter,OwnName,OwnMaster{
    private String name;
    private String workSpace;
    private int volume;
    private int originalPrice;
    private int total;
    public Click click;

    public Item(String name, int volume, int originalPrice, int total) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
    }

    public Item(String name,int volume, int originalPrice) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
    }

    public Item(){}

    public static void addItem(Item values, HashMap<String,Item> items){
        if (items.get(values.name)!=null)
            items.get(values.name).total = items.get(values.name).getTotal()+values.getTotal();
        else items.put(values.name,values);
    }

    public abstract boolean haveTable();
    public abstract void createTable(String name);
    public abstract void saveDate(String workSpaceName);

    //从SQL中读取数据
    public abstract void setSQLDate(Cursor cursor);

    public abstract<T extends Item> T getListItem(String name);

    public void saveIndexDate(String name){
      Sql.operating(new String[]{
      "insert into "+name+ Info.INSTANCE.getINDEX() +" ("+ Info.INSTANCE.getId() +","+ Info.INSTANCE.getNAME() +","+ Info.INSTANCE.getTotal() +") values ('"+getClass().getName()+"','"+ this.name +"',"+total+")"
      });
      createTable(workSpace);
      saveDate(name);
    }

    public static HashMap<String,Item> getIndexDate(String name) {
        //从XML与SQL中获取数据
        HashMap<String,Item> map = new HashMap<>();
        Cursor iDate = Sql.getAllInfo(name+ Info.INSTANCE.getINDEX());
        while (iDate.moveToNext()){
            Item article = Tools.getType(iDate.getString(iDate.getColumnIndex(Info.INSTANCE.getId())));
            article = article.getListItem(iDate.getString(iDate.getColumnIndex(Info.INSTANCE.getNAME())));
            article.total = (iDate.getInt(iDate.getColumnIndex(Info.INSTANCE.getTotal())));
            article.setMaster(name);
            try {
                if (article.haveTable())
                article.setSQLDate(Sql.getCursor(name+Tools.getSuffix(article.getClass().getName()),"*", Info.INSTANCE.getNAME(),new String[]{"'"+article.getName()+"'"}));
            }catch (RuntimeException ignored){
                throw ignored;
            }
            map.put(article.getName(),article);
        }
        iDate.close();
        return map;
    }

    public static<T extends Item> HashMap<String,T> changeToMap(T[] list) {
        HashMap<String,T> items = new HashMap<>();
        for (T item:list) items.put(item.getName(),item);
        return items;
    }

    @Override
    public Map<String, String> UIPageAdapter() {
        return GameUI.getAdapterMap(getName(),"体积:"+ getVolume(),"价格" + getOriginalPrice(),"总量:"+ getTotal());
    }
//
//    public void showMyOwnOnClick(GameUI UI) {
//        String[] choose = new String[1];
//        final Item copy = this;
//        //TODO:修改使容器统一化
//        UI.chooseDialogue("move "+name+" to ...",new String[]{"背包","垃圾桶",Building.getWhere(Player.getPlayerDate().getX_coordinate()).getName()},choose);
//        new Response<String>(choose){
//            @Override
//            public void doThings() {
//                if (choose[0].equals("背包")){
//                    setMaster(Player.getPlayerName());
//                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
//                    addItem(copy,Player.getPlayerDate().getBag());
//                } else if (choose[0].equals(Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getName())&&
//                                workSpace.equals(Player.getPlayerName())){
//                    setMaster(choose[0]);
//                    Player.getPlayerDate().getBag().remove(name);
//                    addItem(copy,Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems());
//                } else if (choose[0].equals("垃圾桶")){
//                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
//                }
//            }
//        };
//    }
//
    @Override
    public void onClick(GameUI gameUI) {
        click.onClick(gameUI,this);
    }


    public static void createIndex(String name) {
        Sql.operating(
        new String[]{
        "create table if not exists " + name + Info.INSTANCE.getINDEX() +" (" + Info.INSTANCE.getId() + " text," + Info.INSTANCE.getNAME() + " text," + Info.INSTANCE.getTotal() + " integer)",
        "DELETE FROM " + name + Info.INSTANCE.getINDEX() +""
        }
        );
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
        if (total<0) Building.findWorkSpace(workSpace).getItems().remove(this.name);
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public String getMaster() {
        return workSpace;
    }

    @Override
    public void setMaster(String master) {
        this.workSpace = master;
    }
}
