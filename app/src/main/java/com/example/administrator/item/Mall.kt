package com.example.administrator.item

import android.database.Cursor

class Mall : Item {

    lateinit var list: Array<Item>

    constructor(name: String, volume: Int, originalPrice: Int, itemList: Array<Item>) : super(name, volume, originalPrice) {
        list = itemList
    }

    constructor() : super()

    override fun haveTable(): Boolean {
        return false
    }

    override fun createTable(name: String) {}

    override fun getSQLDate(cursor: Cursor) {}

    override fun saveDate(workSpaceName: String) {}
    override fun<T: Item> getListItem(name: String): T {
        Item.changeToMap(items)[name]?.let { return it as T}
        error("Item No Found")
    }


    companion object {

        var items = arrayOf<Mall>()
    }

}