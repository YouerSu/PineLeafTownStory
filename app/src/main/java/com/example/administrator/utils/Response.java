package com.example.administrator.utils;

/**
 实现此类,定期检查条件
 */

public abstract class Response<T> extends Thread {

    private T[] list;

    public Response(T[] list){
        this.list = list;
        start();
    }

        @Override
    public void run() {
       while (judgment());
        doThings();
        interrupted();
    }

    public abstract void doThings();

    public boolean judgment(){ return list[0]==null;}

    public T getResult() {
        return list[0];
    }
}
