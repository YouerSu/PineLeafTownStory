package com.example.administrator.utils;

public interface NPC extends ShowAdapter{

    default void startActivity(){

    }

    void saveDate();

    void setGoodValue(int newGoodValue);

    int getGoodValue();

    String getName();

    void setName(String name);

    int getxCoordinate();

    void setxCoordinate(int xCoordinate);


}
