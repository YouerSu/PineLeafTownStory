package com.example.administrator.buildings;

import android.database.Cursor;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.ShowAdapter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//创建新的类型来这登记一下 XD
enum Article {Undeveloped,SellItem}

public abstract class Item implements ShowAdapter {
    String name;
    int volume;
    int originalPrice;
    int total;
    Article article;

    public Item(String name, int volume, int originalPrice, int total, Article article) {
        this.name = name;
        this.volume = volume;
        this.originalPrice = originalPrice;
        this.total = total;
        this.article = article;
    }

    public static Article[] getArticle() {
        return Article.values();
    }

    public Item(){}

    //根据不同情况调整数据
    public abstract void affectedByTheCurrentSituation();

    public abstract void purchase();

    public abstract void setType(Element element);

    public abstract void setType(Cursor cursor);

    public abstract void saveDate(String tableName);

    public abstract void setType(HashMap<String,Item> articles);

    public void saveSuperDate(String tableName){
    //渣渣设计,速度极慢
      GameTime.operatingSql(new String[]{
      "insert into "+tableName+" ("+Info.id+","+Info.NAME+","+Info.total+") values ("+article.name()+","+name+","+total+")"
        });
      saveDate(tableName);
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

    public static HashMap<String,Item> getAllItems(String url) {
        //十分暴力,无能为力
        HashMap<String,Item> items = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File(url));
            Element root =  doc.getRootElement();
            for (Iterator<Element> it = root.elementIterator("item"); it.hasNext();) {
                Element item = it.next();
                Item article = null;
                /**每次有新类型...我没想到更好的办法..*/
                switch (Article.valueOf(item.elementText("type"))){
                    case Undeveloped: continue;
                    case SellItem: article = new SellItem();   break;

                }
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

    public static List<Item> getDate(String tableName) {
        List<Item> list = new ArrayList<>();
        Cursor iDate = GameTime.getCursor(tableName);
        while (iDate.moveToNext()){
            HashMap<String,Item> articles = null;
            Item article = null;
            switch (Article.valueOf(iDate.getString(iDate.getColumnIndex(Info.id)))){
                /**同上...感觉这两方法一模一样...感觉游戏出来要卡死...*/
                case  SellItem:
                    articles = getAllItems("res\\xml\\sell_items.xml");
                    article = new SellItem();
                    break;
            }
        article.setName(iDate.getString(iDate.getColumnIndex(Info.NAME)));
        article.setTotal(iDate.getInt(iDate.getColumnIndex(Info.total)));
        article.setVolume(articles.get(article.getName()).getVolume());
        article.setType(iDate);
        article.setType(articles);
        }
        iDate.close();
        return list;
    }

}
