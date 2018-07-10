package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.ShowAdapter;
import com.example.administrator.utils.Sql;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Item implements ShowAdapter{
    String name;
    String workSpace;
    int volume;
    int originalPrice;
    int total;

    public Item(String name, int volume, int originalPrice, int total) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
    }

    public abstract void purchase();

    public abstract void getDate(Element element);

    public abstract void getDate(String name, String ItemName);

    public abstract void saveDate(String tableName);

    public abstract void getDate(HashMap<String,Item> articles);

    public void saveSuperDate(String name){
    //渣渣设计,速度极慢
      Sql.operatingSql(new String[]{
      "insert into "+name+"Index ("+Info.id+","+Info.NAME+","+Info.total+") values ("+getClass().getName()+","+ this.name +","+total+")"
        });
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
        return GameTime.getAdapterMap(getName(),"体积:"+ getVolume(),"价格" + getOriginalPrice(),"总量:"+ getTotal());
    }

    @Override
    public void showMyOwnOnClick(GameUI UI) {
        if (!UI.trueOrFalseDialogue("将"+name+"从你的仓库移除"))return;
            int amount = UI.reAmount("输入移除的数量");
            if (amount<=0) return;
            setTotal(getTotal() - amount);
            Character.getFirstMaster(Character.findMaster(getWorkSpace(),Building.buildings)).removeItem(this);
            UI.dialogueBox("移除成功");
    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI) {
        if (UI.reAmount("输入购买总数")<=0)return;
            UI.dialogueBox("购买成功");
        Character.getFirstMaster(Character.findMaster(getWorkSpace(),Building.buildings)).addItems(this);
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public static HashMap<String,Item> getAllItems(String url) {
        //从XML里读取数据
        HashMap<String,Item> items = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File(url));
            Element root =  doc.getRootElement();
            for (Iterator<Element> it = root.elementIterator("item"); it.hasNext();) {
                Element item = it.next();
                Item article = GameTime.getType(item.elementText("type"));
                article.setName(item.elementText("name"));
                article.setOriginalPrice(Integer.valueOf(item.elementText("original")));
                article.setTotal(Integer.valueOf(item.elementText("total")));
                article.setVolume(Integer.valueOf(item.elementText("volume")));
                article.getDate(item);
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
        HashMap<String,Item> articles = getAllItems("res\\xml\\"+iDate.getString(iDate.getColumnIndex(Info.id))+".xml");
        article.setWorkSpace(name);
        article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
        article.setVolume(articles.get(article.getName()).getVolume());
        article.setOriginalPrice(articles.get(article.getName()).getOriginalPrice());
        article.getDate(name,article.getName());
        article.getDate(articles);
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
