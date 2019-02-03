package com.example.administrator.item

import android.database.Cursor

import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameUI
import com.example.administrator.utils.Info
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.utils.Sql
import com.example.administrator.utils.Tools

import java.util.HashMap


abstract class Item : ShowAdapter{
    lateinit var name: String
    lateinit var workSpace: String
    var volume: Int = 0
    var originalPrice: Int = 0
    var total: Int = 0
        set(value) {
            field = value
            if (total <= 0) Building.findWorkSpace(workSpace).items.remove(this.name)
        }
    override lateinit var click: (GameUI, ShowAdapter) -> Unit

    constructor(name: String, volume: Int, originalPrice: Int, total: Int) {
        this.name = name
        this.volume = volume
        this.originalPrice = originalPrice
        this.total = total
    }

    constructor(name: String, volume: Int, originalPrice: Int) {
        this.name = name
        this.volume = volume
        this.originalPrice = originalPrice
    }

    constructor()

    abstract fun haveTable(): Boolean
    abstract fun createTable(name: String)
    abstract fun saveDate(workSpaceName: String)

    //从SQL中读取数据
    abstract fun setSQLDate(cursor: Cursor)

    abstract fun <T : Item> getListItem(name: String): T

    fun saveIndexDate(workSpace: String) {
        Sql.operating(arrayOf("insert into ${workSpace + Info.INDEX} (${Info.id},${Info.name},${Info.total}) values ('${javaClass.name}','$name',$total)"))
        createTable(this.workSpace)
        saveDate(workSpace)
    }

    override fun UIPageAdapter(): Map<String, String> {
        return GameUI.getAdapterMap(name, "体积:$volume", "价格$originalPrice", "总量:$total" )
    }

    //
    //    public void showMyOwnOnClick(GameUI UI) {
    //        String[] choose = new String[1];
    //        final Item copy = this;
    //        //TODO:修改使容器统一化
    //        UI.chooseDialogue("move "+name+" to ...",new String[]{"背包","垃圾桶",Building.getWhere(Player.getPlayerDate().getX_coordinate()).name},choose);
    //        new Response<String>(choose){
    //            @Override
    //            public void doThings() {
    //                if (choose[0].equals("背包")){
    //                    workSpace = (Player.getPlayerName());
    //                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
    //                    addItem(copy,Player.getPlayerDate().getBag());
    //                } else if (choose[0].equals(Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).name)&&
    //                                workSpace.equals(Player.getPlayerName())){
    //                    workSpace = (choose[0]);
    //                    Player.getPlayerDate().getBag().remove(name);
    //                    addItem(copy,Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems());
    //                } else if (choose[0].equals("垃圾桶")){
    //                    Building.getBuildings().get(Player.getPlayerDate().getX_coordinate()).getItems().remove(name);
    //                }
    //            }
    //        };
    //    }
    //
    override fun onClick(gameUI: GameUI) {
        click(gameUI, this)
    }

    companion object {

        fun addItem(values: Item, items: HashMap<String, Item>) {
            if (items[values.name] != null)
                items[values.name]?.let { it.total += values.total }
            else
                items[values.name] = values
        }

        fun getIndexDate(name: String): HashMap<String, Item> {
            //从List与SQL中获取数据
            val map = HashMap<String, Item>()
            val iDate = Sql.getAllInfo(name + Info.INDEX)
            while (iDate.moveToNext()) {
                var article = Tools.getType<Item>(iDate.getString(iDate.getColumnIndex(Info.id)))
                article = article.getListItem(iDate.getString(iDate.getColumnIndex(Info.NAME)))
                article.total = iDate.getInt(iDate.getColumnIndex(Info.total))
                article.workSpace = name
                try {
                    if (article.haveTable())
                        article.setSQLDate(Sql.getCursor(name + Tools.getSuffix(article.javaClass.name), "*", Info.NAME, arrayOf("'" + article.name + "'")))
                } catch (ignored: RuntimeException) {
                    throw ignored
                }

                map[article.name] = article
            }
            iDate.close()
            return map
        }

        fun <T : Item> changeToMap(list: Array<T>): HashMap<String, T> {
            val items = HashMap<String, T>()
            for (item in list) items[item.name] = item
            return items
        }


        fun createIndex(name: String) {
            Sql.operating(
                    arrayOf("create table if not exists " + name + Info.INDEX + " (" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)", "DELETE FROM " + name + Info.INDEX + "")
            )
        }
    }
}
