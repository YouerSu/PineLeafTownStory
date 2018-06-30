package com.example.administrator.utils;

import com.example.administrator.buildings.Building;

import java.util.Map;

public interface ShowAdapter {

     Map<String,String> UIPageAdapter();

     void showMyOwnOnClick(GameUI UI,Building building);

     void showNotMyOwnOnClick(GameUI UI, Building building);

}
