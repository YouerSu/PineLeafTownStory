package com.example.administrator.character;

public interface NPC{

    //开启线程
    default void startActivity() {
        new Thread(()->{
            behavior();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //NPC的行为
    void behavior();
}
