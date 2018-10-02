package com.example.administrator.character;


import com.example.administrator.buildings.ShowAdapter;

public interface NPC extends ShowAdapter {

    //开启线程
    default void startActivity() {
        new Thread(()->{
            if (status()) start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //NPC的行为
    void start();
    boolean status();


}
