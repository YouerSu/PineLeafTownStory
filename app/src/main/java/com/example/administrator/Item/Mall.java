package com.example.administrator.Item;


import org.dom4j.Element;

import java.util.HashMap;

public class Mall extends Item {

    public Mall(String name, int volume, int originalPrice, int total) {
        super(name, volume, originalPrice, total);
    }

    @Override
    public void purchase() {

    }

    @Override
    public void getXMLDate(Element element) {

    }

    @Override
    public void getDate(String name, String ItemName) {

    }

    @Override
    public void saveDate(String tableName) {

    }

    @Override
    public void getDate(HashMap<String, Item> articles) {

    }
}
