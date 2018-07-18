package com.example.administrator.utils;

/**
 实现此类,定期检查条件
 */

public class Response<T> extends Thread {

    T[] list;

    public Response(T[] list){
        this.list = list;
        start();
    }

        @Override
    public void run() {
        if (judgment()) return;
        doThings();
        interrupted();
    }

    public void doThings(){}

    public boolean judgment(){ return list[0]==null;}

    public T getResult() {
        return list[0];
    }
}
