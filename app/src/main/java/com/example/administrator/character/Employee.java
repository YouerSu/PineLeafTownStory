package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.utils.Tools.findMaster;

public abstract class Employee extends Character {


    public abstract boolean work(Item item);

    @Override
    public void onClick(GameUI gameUI) {
        if (getWorkSpace().equals(Player.getPlayerName())||
            Building.findWorkSpace(getWorkSpace())
            .getMaster()
            .equals(Player.getPlayerName())){
            showMyOwnOnClick(gameUI);
        } else{
            showNotMyOwnOnClick(gameUI);
        }
    }

    protected void showNotMyOwnOnClick(GameUI gameUI) {
        String[] choose = new String[1];
        gameUI.chooseDialogue("你好", new String[]{"雇佣","离开"}, choose);
        new Response<String>(choose) {
            @Override
            public void doThings() {
                if (getResult().equals("雇佣"))
                    if (getWorkSpace().equals("PineTower")){
                        setWorkSpace(Player.getPlayerName());
                }
            }
        };
    }

    protected void showMyOwnOnClick(GameUI gameUI) {
        List<String> messages = new ArrayList<>();
        String[] choose = new String[1];
        for (Building building : Building.getBuildings()) {
            if (building.getName().equals(Player.getPlayerName())) {
                messages.add(building.getName());
            }
        }
        gameUI.chooseDialogue("选择工作场所",messages.toArray(),choose);
        new Response<String>(choose){
            @Override
            public void doThings() {
                while (choose[0]!=null);
                setWorkSpace(choose[0]);
                interrupted();
            }
        };

    }

    @Override
    void initialization() {

    }
}
