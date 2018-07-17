package com.example.administrator.character;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.GameUI;
import com.example.administrator.item.Item;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends Character {


    public abstract boolean work(Item item);

    @Override
    public void onClick(GameUI gameUI) {
        String[] choose = new String[1];
        gameUI.chooseDialogue("你好",new String[]{"雇佣"},choose);
        new Response<String>(choose){
            @Override
            public void run() {
                try {
                    wait(8000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getList()[0].equals("雇佣")){
                    if (Tools.getFirstMaster(Tools.findMaster(Tools.getFirstMaster(Tools.findMaster(getWorkSpace(), Building.getBuildings())).getMaster(),Character.getCharacters()))==null||Tools.getFirstMaster(Tools.findMaster(Tools.getFirstMaster(Tools.findMaster(getWorkSpace(), Building.getBuildings())).getMaster(),Character.getCharacters())).getPrestige()<Player.getPlayerDate().getPrestige()){
                        List<String> messages = new ArrayList<>();
                        String[] choose = new String[1];
                        for (Building building:Tools.findMaster(Player.getPlayerName(),Building.getBuildings()))
                            messages.add(building.getName());
                        gameUI.chooseDialogue("选择工作场所",messages.toArray(),choose);
                        new Response<String>(choose){
                            @Override
                            public void run() {
                                while (choose[0]!=null);
                                setWorkSpace(choose[0]);
                                interrupted();
                            }
                        }.start();
                    }
                }
                else gameUI.dialogueBox(getName()+"走开了");
                interrupted();
            }
        }.start();
    }

    @Override
    void initialization() {

    }
}
