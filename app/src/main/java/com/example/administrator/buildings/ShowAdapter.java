package com.example.administrator.buildings;

import com.example.administrator.buildings.GameUI;

import java.util.Map;

public interface ShowAdapter {

     Map<String,String> UIPageAdapter();

     void showMyOwnOnClick(GameUI UI);

     void showNotMyOwnOnClick(GameUI UI);

}
