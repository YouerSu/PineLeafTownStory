package com.example.administrator.item;


import android.database.Cursor;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.character.Player;
import com.example.administrator.utils.Info;
import org.dom4j.Element;
import java.util.HashMap;
import java.util.List;

public class Mall extends Item {

    private String xml;

    public Mall(String name, int volume, int originalPrice, int total, String xml) {
        super(name, volume, originalPrice, total);
        this.xml = xml;
    }

    public Mall() {
    }

    @Override
    public void createItemTable(String name) {
    }

    @Override
    public void getXMLDate(Element element) {
        xml = element.elementText(Info.XML);
    }

    @Override
    public void getSQLDate(Cursor cursor) {

    }

    @Override
    public void saveDate(String tableName) {

    }

    @Override
    public void getListDate(HashMap<String, Item> articles) {
        setXml(((Mall)articles.get(getName())).getXml());
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }


    @Override
    public void showOnClick(GameUI gameUI) {
    gameUI.showListDialogue((List<Item>)getAllItems(xml).values());
    }


}
