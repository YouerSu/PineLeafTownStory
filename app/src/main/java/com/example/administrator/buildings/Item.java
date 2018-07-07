package com.example.administrator.buildings;

import android.database.Cursor;

import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.ShowAdapter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//创建新的类型来这登记一下 XD
enum Article {Undeveloped,SellItem}

public abstract class Item implements ShowAdapter{
    String name;
    int volume;
    int originalPrice;
    int total;

    public Item(String name, int volume, int originalPrice, int total) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
    }

    public static Article[] getArticleList() {
        return Article.values();
    }

    //根据不同情况调整数据
    public abstract void affectedByTheCurrentSituation();

    public abstract void purchase();

    public abstract void setType(Element element);

    public abstract void setType(String name);

    public abstract void saveDate(String tableName);

    public abstract void setType(HashMap<String,Item> articles);

    public void saveSuperDate(String name){
    //渣渣设计,速度极慢
      GameTime.operatingSql(new String[]{
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

    @Override
    public Map<String, String> UIPageAdapter() {
        return GameTime.getAdapterMap(getName(),"体积:"+ getVolume(),"价格" + getOriginalPrice(),"总量:"+ getTotal());
    }

    @Override
    public void showMyOwnOnClick(GameUI UI,Building building) {
        if (!UI.trueOrFalseDialogue("将"+name+"从你的仓库移除"))return;
            int amount = UI.reAmount("输入移除的数量");
            if (amount<0) return;
            setTotal(getTotal() - amount);
            building.removeItem(this);
            UI.dialogueBox("移除成功");
    }

    @Override
    public void showNotMyOwnOnClick(GameUI UI,Building building) {
        if (UI.reAmount("输入购买总数")<=0)return;
            UI.dialogueBox("购买成功");
            building.addItems(this);
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
                article.setTotal(Integer.valueOf(item.elementText("total")));
                article.setVolume(Integer.valueOf(item.elementText("volume")));
                article.setType(item);
                items.put(article.name,article);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static HashMap<String,Item> getDate(String tableName) {
        //从XML与SQL中获取数据
        HashMap<String,Item> map = new HashMap<>();
        Cursor iDate = GameTime.getCursorAllInformation(tableName);
        while (iDate.moveToNext()){
        Item article = GameTime.getType(iDate.getString(iDate.getColumnIndex(Info.id)));
        //垃圾设计
        HashMap<String,Item> articles = getAllItems("res\\xml\\"+iDate.getString(iDate.getColumnIndex(Info.id))+".xml");
        article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
        article.setVolume(articles.get(article.getName()).getVolume());
        article.setType(article.getName());
        article.setType(articles);
        map.put(article.getName(),article);
        }
        iDate.close();
        return map;
    }

    public static void createTable(String name) {
        GameTime.operatingSql(//," + Info.sellPrice + " integer
        new String[]{
        "create table if not exists " + name + "Index(" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)",
        "DELETE FROM " + name + "Index"
        }
        );
    }
}
