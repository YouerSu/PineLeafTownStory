package com.example.administrator.utils;

import android.content.Context;

/*  创建数据库
    获取数据库  */

//13020013180359 76596182 name

public class Db {
    private static Sql Info;

    public static Sql setInfo(Context context){
        if (Info==null) {
            Info = new Sql(context);
           }
        return Info;
    }



}
