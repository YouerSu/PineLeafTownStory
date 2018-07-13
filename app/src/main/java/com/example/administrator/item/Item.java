package com.example.administrator.item;

import android.database.Cursor;

import com.example.administrator.character.Character;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameTime;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Sql;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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


    public abstract void createItemTable(String name);

    //从XML读取额外数据
    public abstract void getXMLDate(Element element);
    //从SQL中读取数据
    public abstract void getSQLDate(Cursor cursor);

    public abstract void saveDate(String workSpaceName);
    //从数据集合中读取额外数据
    public abstract void getListDate(HashMap<String,Item> articles);

    public void saveSuperDate(String name){
    //渣渣设计,速度极慢
      Sql.operatingSql(new String[]{
      "insert into "+name+Info.INDEX+" ("+Info.id+","+Info.NAME+","+Info.total+") values ("+getClass().getName()+","+ this.name +","+total+")"
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
        if (!UI.trueOrFalseDialogue("将"+name+"从你的仓库移除"))return;
            int amount = UI.reAmount("输入移除的数量");
            if (amount<=0) return;
            setTotal(getTotal() - amount);
            Character.getFirstMaster(Character.findMaster(getWorkSpace(), Building.buildings)).removeItem(this);
            UI.dialogueBox("移除成功");
    }

    @Override
    public void showOnClick(GameUI gameUI) {
        if (Character.getFirstMaster(Character.findMaster(workSpace,Building.getBuildings())).getMaster().equals(Info.YOU))
            showMyOwnOnClick(gameUI);
        else
            showNotMyOwnOnClick(gameUI);
    }

    public void showNotMyOwnOnClick(GameUI UI) {
        if (UI.reAmount("输入购买总数")<=0)return;
            UI.dialogueBox("购买成功");
        Character.getFirstMaster(Character.findMaster(getWorkSpace(),Building.buildings)).addItems(this);
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public static HashMap<String,Item> getAllItems(String name) {
        //从XML里读取数据
        HashMap<String,Item> items = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("res\\xml\\"+name+".xml"));
            Element root =  doc.getRootElement();
            for (Iterator<Element> it = root.elementIterator("item"); it.hasNext();) {
                Element item = it.next();
                Item article = GameTime.getType(item.elementText("type"));
                article.setName(item.elementText("name"));
                article.setOriginalPrice(Integer.valueOf(item.elementText("original")));
                article.setTotal(Integer.valueOf(item.elementText("total")));
                article.setVolume(Integer.valueOf(item.elementText("volume")));
                article.getXMLDate(item);
                items.put(article.name,article);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static HashMap<String,Item> getSuperDate(String name) {
        //从XML与SQL中获取数据
        HashMap<String,Item> map = new HashMap<>();
        Cursor iDate = Sql.getCursorAllInformation(name+Info.ITEM);
        while (iDate.moveToNext()){
        Item article = GameTime.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
        HashMap<String,Item> articles = getAllItems(iDate.getString(iDate.getColumnIndex(Info.id)).substring(iDate.getString(iDate.getColumnIndex(Info.id)).lastIndexOf(".")+1));
        article.setWorkSpace(name);
        article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
        article.setVolume(articles.get(article.getName()).getVolume());
        article.setOriginalPrice(articles.get(article.getName()).getOriginalPrice());
        article.getSQLDate(Sql.getCursor(name+article.getClass().getName().substring(article.getClass().getName().lastIndexOf("."+1)),Info.sellPrice,Info.NAME,new String[]{article.getName()}));
        article.getListDate(articles);
        map.put(article.getName(),article);
        }
        iDate.close();
        return map;
    }

    public static void createTable(String name) {
        Sql.operatingSql(//," + Info.sellPrice + " integer
        new String[]{
        "create table if not exists " + name + "Index(" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)",
        "DELETE FROM " + name + "Index"
        }
        );
    }
}
