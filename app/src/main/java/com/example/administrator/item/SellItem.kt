package com.example.administrator.item

import android.database.Cursor

import com.example.administrator.buildings.Building
import com.example.administrator.utils.Info
import com.example.administrator.utils.Sql
import com.example.administrator.utils.Tools

class SellItem : Item() {
    //TODO:将SellItem变为委托类
    @get:Synchronized
    var sellPrice: Int = 0
        private set

    val item: Item
        get() = Building.findWorkSpace(workSpace).items[super.name]!!

    override fun createTable(workSpace: String) {
        Sql.operating(arrayOf("create table if not exists " + workSpace + Tools.getSuffix(javaClass.name) + "(" + Info.NAME + " text," + Info.sellPrice + " integer)", "DELETE FROM " + workSpace + "SellItem"))
    }

    override fun <T : Item> getListItem(name: String): T {
        this.name = name
        return this as T
    }

    override fun haveTable(): Boolean {
        return true
    }

    override fun getSQLDate(cursor: Cursor) {
        sellPrice = cursor.getInt(cursor.getColumnIndex(Info.sellPrice))
    }

    override fun saveDate(name: String) {
        createTable(name)
        Sql.operating(arrayOf("insert into " + name + Tools.getSuffix(javaClass.name) + " (" + Info.NAME + "," + Info.sellPrice + ") values(" + name + "," + sellPrice + ")"))
    }

    //    @Override
    //    public void showNotMyOwnOnClick(GameUI UI) {
    //        String[] price = new String[1];
    //        UI.reText("输入销售价格",price);
    //        super.showNotMyOwnOnClick(UI);
    //        new Response<String>(price){
    //            @Override
    //            public void doThings() {
    //                setSellPrice(Integer.valueOf(price[0]));
    //            }
    //        };
    //    }


    override fun toString(): String {
        return this.name + "(Sell)"
    }

}