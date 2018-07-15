package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.character.Character;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.character.Player;
import com.example.administrator.utils.Info;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;

import java.util.HashMap;
import java.util.Map;


public abstract class Item implements ShowAdapter,OwnName{
    private String name;
    private String workSpace;
    private int volume;
    private int originalPrice;
    private int total;

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

    public abstract void createItemTable(String name);

    //从SQL中读取数据
    public abstract void getSQLDate(Cursor cursor);

    public abstract Item[] getInfoDate();

    public abstract void saveDate(String workSpaceName);
    //从数据集合中读取额外数据
    public abstract void getListDate(HashMap<String,Item> articles);

    public void saveSuperDate(String name){
    //渣渣设计,速度极慢
      Sql.operatingSql(new String[]{
      "insert into "+name+Info.INDEX+" ("+Info.id+","+Info.NAME+","+Info.total+") values ('"+getClass().getName()+"','"+ this.name +"',"+total+")"
      });
      createItemTable(workSpace);
      saveDate(name);
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

    public String getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
    }

    @Override
    public Map<String, String> UIPageAdapter() {
        return GameUI.getAdapterMap(getName(),"体积:"+ getVolume(),"价格" + getOriginalPrice(),"总量:"+ getTotal());
    }

    public void showMyOwnOnClick(GameUI UI) {
        Integer[] amount = new Integer[]{-1};
        final String master = name;
        UI.reText("Enter the number of removals",amount);
        new Response<Integer>(amount){
            @Override
            public void run() {
                try {
                    wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setTotal(getTotal() - amount[0]);
                Character.getFirstMaster(Character.findMaster(getWorkSpace(), Building.buildings)).getItems().get(master).setTotal(-Math.abs(amount[0]));
                UI.dialogueBox("移除成功");
                interrupted();
            }
        }.start();
    }

    @Override
    public void showOnClick(GameUI gameUI) {
        if (Character.getFirstMaster(Character.findMaster(workSpace,Building.getBuildings())).getMaster().equals(Player.getPlayerName()))
            showMyOwnOnClick(gameUI);
        else
            showNotMyOwnOnClick(gameUI);
    }

    public void showNotMyOwnOnClick(GameUI UI) {
        Integer[] amount = new Integer[]{-1};
        final Item item = this;
        UI.reText("Enter the number of buy",amount);
        new Response<Integer>(amount){
            @Override
            public void run() {
                try {
                    wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setTotal(getTotal() - amount[0]);
                Player.getPlayerDate().getBag().put(item.getName(),item);
                UI.dialogueBox("购买成功");
                interrupted();
            }
        }.start();
    }
//        if (UI.reAmount("输入购买总数")<=0)return;
//        UI.dialogueBox("购买成功");
//        Character.getFirstMaster(Character.findMaster(getWorkSpace(),Building.buildings)).addItems(this);
//        if ((Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems() == null))
//            Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().put(name, this);
//        else
//            Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().get(name).setTotal(Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().get(name).getTotal() + getTotal());



    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public static HashMap<String,Item> getAllItems(String name) {
        HashMap<String,Item> items = new HashMap<>();
       for (Item item:((Item)GameTime.getType(name)).getInfoDate())
            items.put(item.getName(),item);
        return items;
    }

    public static HashMap<String,Item> getSuperDate(String name) {
        //从XML与SQL中获取数据
        HashMap<String,Item> map = new HashMap<>();
        Cursor iDate = Sql.getCursorAllInformation(name+Info.INDEX);
        while (iDate.moveToNext()){
        Item article = GameTime.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
        HashMap<String,Item> articles = getAllItems(iDate.getString(iDate.getColumnIndex(Info.id)));
        article.setWorkSpace(name);
        article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
        article.setVolume(articles.get(article.getName()).getVolume());
        article.setOriginalPrice(articles.get(article.getName()).getOriginalPrice());
        try {
            article.getSQLDate(Sql.getCursor(name+article.getClass().getName().substring(article.getClass().getName().lastIndexOf(".")+1),Info.sellPrice,Info.NAME,new String[]{article.getName()}));
        }catch (RuntimeException noSuchTable){}
        article.getListDate(articles);
        map.put(article.getName(),article);
        }
        iDate.close();
        return map;
    }

    public static void createTable(String name) {
        Sql.operatingSql(
        new String[]{
        "create table if not exists " + name + Info.INDEX+" (" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)",
        "DELETE FROM " + name + Info.INDEX+""
        }
        );
    }
}
