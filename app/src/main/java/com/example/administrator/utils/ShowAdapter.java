package com.example.administrator.utils;

import com.example.administrator.buildings.Building;

import java.util.Map;

public interface ShowAdapter {

     Map<String,String> UIPageAdapter();

     void showMyOwnOnClick(GameUI UI);

     void showNotMyOwnOnClick(GameUI UI);

}
