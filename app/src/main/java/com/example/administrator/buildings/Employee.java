package com.example.administrator.buildings;

import android.database.Cursor;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Employee implements NPC{

    private boolean status;



    public static void createTable(String name){
        GameTime.operatingSql(new String[]{
        "create table if not exists " + name + "Employee" + "(" + Info.id + " text," + Info.NAME + " text," + salary + " integer," + Info.GOOD_VALUE + " integer," + Info.ABILITY + " integer," + Info.STATUS + " integer)",
        "DELETE FROM " + name + Info.CHARACTER,
        });
    }

    public abstract boolean work(Item item);

    public abstract void setType();

    public abstract void disasterEvent();

    public abstract void saveDate(String tableName);





    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
