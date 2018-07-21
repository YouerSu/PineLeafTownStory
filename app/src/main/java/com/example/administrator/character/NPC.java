package com.example.administrator.character;


import com.example.administrator.buildings.ShowAdapter;

public interface NPC extends ShowAdapter {

    //开启线程
    default void startActivity() {
        new Thread(()->{
            work();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //NPC的行为
    void work();


}
