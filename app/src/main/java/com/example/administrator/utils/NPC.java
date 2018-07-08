package com.example.administrator.utils;


public interface NPC extends ShowAdapter{

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
