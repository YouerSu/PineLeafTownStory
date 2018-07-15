package com.example.administrator.utils;

import com.example.administrator.item.Item;
import com.example.administrator.item.Mall;
import com.example.administrator.item.SellItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Info {
    public static final int
    VER = 1,
    BUILDING_PRICE = 20000;

    public static final long
    SPEED = 10000;

    public static final String
    name = "Info",
    PLAYER = "Player",
    BUILDING = "Building",
    NAME = "name",
    MONEY = "money",
    PRESTIGE = "prestige",
    SECURITY = "security",
    id = "_id",
    FACILITIES = "facilities",
    GOOD_VALUE = "GoodValue",
    salary = "salary",
    capacity = "capacity",
    customer = "customer",
    MINUTE = "minute",
    HOUR = "hour",
    MONTH = "month",
    YEAR = "year",
    DAY = "day",
    VOLUME = "volume",
    OPRICE = "Oprice",
    POPULAR = "popular",
    sellPrice = "sellPrice",
    total = "total",
    loan = "loan",
    loanTime = "loanTime",
    coordinate = "coordinate",
    ABILITY = "ability",
    DIFFERENT_WORLD = "differentWorld",
    PLACE_NAME = "松叶镇",
    LT1 ="lt1",
    LT2 ="lt2",
    LT3 ="lt3" ,
    STATUS ="status",
    CHARACTER = "Character",
    MASTER = "Master",
    ITEM = "Item",
    TIME = "Time",
    XML = "xml",
    YOU = "You",
    INDEX = "Index";

    public static abstract class ITEM{
        public abstract Item[] getItems();

    }

    public static class SELLITEM extends ITEM{
        SellItem[] sellItems = new SellItem[]{

        };

        @Override
        public Item[] getItems() {
            return sellItems;
        }
    }

    public static final class MALL extends ITEM{
        Mall[] sellItems = new Mall[]{
            new Mall("SellItem",0,0,"SELLITEM"),

        };

        @Override
        public Item[] getItems() {
            return sellItems;
        }
    }

}
