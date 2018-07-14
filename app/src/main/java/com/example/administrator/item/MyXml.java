package com.example.administrator.item;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class MyXml {

    public static Document getXml(String name){
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read("app/src/main/java/com/example/administrator/item/"+name+".xml");

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

}
