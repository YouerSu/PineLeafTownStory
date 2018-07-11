package com.example.administrator.utils;

/**
 实现此类,定期检查条件
 */

public class Response<T> extends Thread {

    T[] list;

    public Response(T[] list) {
        this.list = list;
    }

//    @Override
//    public void run() {
//        if (list!=null)
//        ...doSomeThings...
//        end
//    }
}
