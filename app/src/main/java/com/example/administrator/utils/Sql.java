package com.example.administrator.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.storeboss.Game;

public class Sql extends SQLiteOpenHelper{
    public Sql(Context context) {
        super(context,Info.name,null,Info.ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+Info.Tp+"("+Info.id+" integer primary key,"+Info.Tn+" text not null,"+Info.Tm+" integer,"+Info.Tprestige+" integer,"+Info.lastminute+" integer,"+Info.lasthour+" integer,"+Info.lastday+" integer,"+Info.lastmonth+" integer,"+Info.lastyear+" integer,"+Info.investment+" integer,"+Info.loan+" integer,"+Info.loanTime+" integer,"+Info.nowPager+" integer)");
        db.execSQL("insert into "+Info.Tp+"("+Info.id+","+Info.Tn+","+Info.Tm+","+Info.Tprestige+","+Info.lastminute+","+Info.lasthour+","+Info.lastday+","+Info.lastmonth+","+Info.lastyear+","+Info.loan+","+Info.loanTime+","+Info.nowPager+") values(1,'啦啦啦,我是没有名字的傻瓜',0,0,10,10,7,3,1,0,0,1)");
        db.execSQL("create table if not exists "+Info.Ts+0+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.Tclever+" integer,"+Info.sl+" integer,"+Info.loy+" integer,"+Info.salary+" integer not null,"+Info.capacity+" integer,"+Info.customer+" integer)");
        db.execSQL("insert into "+Info.Ts+0+"("+Info.Tn+","+Info.Tclever+","+Info.sl+","+Info.loy+","+Info.salary+","+Info.capacity+","+Info.customer+") values('建筑',100,20,2,0,550,0)");
        db.execSQL("create table if not exists "+Info.wareHouse+0+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.sellPrice+" integer,"+Info.total+" integer)");
        db.execSQL("insert into "+Info.wareHouse+0+"("+Info.Tn+","+Info.sellPrice+","+Info.total+") values('杨梅',10,50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
