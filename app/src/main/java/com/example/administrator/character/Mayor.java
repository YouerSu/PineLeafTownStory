package com.example.administrator.character;

public class Mayor extends Character implements NPC{

    @Override
    void initialization() {
        startActivity();
    }

    @Override
    public void work() {
        //TODO:调配NPC的职业
    }
}
