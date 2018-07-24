package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.character.Character;
import com.example.administrator.character.Player;
import com.example.administrator.utils.Info;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.utils.Tools.findMaster;


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

    public static void addItem(Item values, HashMap<String,Item> items){
        if (items.get(values.name)!=null)
            items.get(values.name).setTotal(items.get(values.name).getTotal()+values.getTotal());
        else items.put(values.name,values);
    }

     public abstract void createItemTable(String name);

    //从SQL中读取数据
    public abstract void getSQLDate(Cursor cursor);

    public abstract void saveDate(String workSpaceName);
    //从数据集合中读取额外数据
    public abstract void getListDate(HashMap<String,Item> articles);

    public abstract Item[] getAllDate();

    public void saveSuperDate(String name){
    //渣渣设计,速度极慢
      Sql.operatingSql(new String[]{
      "insert into "+name+Info.INDEX+" ("+Info.id+","+Info.NAME+","+Info.total+") values ('"+getClass().getName()+"','"+ this.name +"',"+total+")"
      });
      createItemTable(workSpace);
      saveDate(name);
    }

    public static HashMap<String,Item> getSuperDate(String name) {
        //从XML与SQL中获取数据
        HashMap<String,Item> map = new HashMap<>();
        Cursor iDate = Sql.getCursorAllInformation(name+Info.INDEX);
        while (iDate.moveToNext()){
            Item article = Tools.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
            HashMap<String,Item> articles = getAllItems(iDate.getString(iDate.getColumnIndex(Info.id)));
            article.setWorkSpace(name);
            article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
            article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
            article.setVolume(articles.get(article.getName()).getVolume());
            article.setOriginalPrice(articles.get(article.getName()).getOriginalPrice());
            try {
                article.getSQLDate(Sql.getCursor(name+article.getClass().getName().substring(article.getClass().getName().lastIndexOf(".")+1),Info.sellPrice,Info.NAME,new String[]{article.getName()}));
            }catch (RuntimeException ignored){}
            article.getListDate(articles);
            map.put(article.getName(),article);
        }
        iDate.close();
        return map;
    }

    public static HashMap<String,Item> getAllItems(String name) {
        HashMap<String,Item> items = new HashMap<>();
        for (Item item:((Item) Tools.getType(name)).getAllDate())
            items.put(item.getName(),item);
        return items;
    }

    @Override
    public Map<String, String> UIPageAdapter() {
        return GameUI.getAdapterMap(getName(),"体积:"+ getVolume(),"价格" + getOriginalPrice(),"总量:"+ getTotal());
    }

    public void showMyOwnOnClick(GameUI UI) {
        String[] choose = new String[1];
        final Item copy = this;
        UI.chooseDialogue("move "+name+" to ...",new String[]{"背包","垃圾桶",Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getName()},choose);
        new Response<String>(choose){

            @Override
            public void doThings() {
                if (choose[0].equals("背包")){
                    setWorkSpace(Player.getPlayerName());
                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
                    addItem(copy,Player.getPlayerDate().getBag());
                } else if (choose[0].equals(Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getName())&&
                                workSpace.equals(Player.getPlayerName())){
                    setWorkSpace(choose[0]);
                    Player.getPlayerDate().getBag().remove(name);
                    addItem(copy,Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems());
                } else if (choose[0].equals("垃圾桶")){
                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
                }
            }
        };
    }

    @Override
    public void onClick(GameUI gameUI) {
        if (Tools.findMaster(Building.findWorkSpace(getWorkSpace()).getMaster(), Character.getCharacters())
            .getName().equals(Player.getPlayerName())) {
            showMyOwnOnClick(gameUI);
        } else {
            showNotMyOwnOnClick(gameUI);
        }
    }

    public void showNotMyOwnOnClick(GameUI UI) {
        String[] amount = new String[1];
        final Item item = this;
        UI.reText("Enter the number of buy",amount);
        new Response<String>(amount){
            @Override
            public void doThings() {
                item.setTotal(Integer.valueOf(amount[0]));
                Item.addItem(item,Player.getPlayerDate().getBag());
            }
        };
    }

    public static void createTable(String name) {
        Sql.operatingSql(
        new String[]{
        "create table if not exists " + name + Info.INDEX+" (" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)",
        "DELETE FROM " + name + Info.INDEX+""
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
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
    }
}
