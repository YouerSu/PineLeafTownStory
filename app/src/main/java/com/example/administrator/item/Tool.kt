package com.example.administrator.item

import android.database.Cursor
import com.example.administrator.buildings.Building
import com.example.administrator.character.Employee
import java.util.*

open
class Tool(name: String?, volume: Int, originalPrice: Int) : Item(name, volume, originalPrice) {

    var items: Array<Item> = arrayOf(
            Tool("售卖机", 10, 100)
    )

    fun findWorker(building: Building, item: Item): Employee?{
        for (employee in building.employees)
            if (employee.receive(item)) return employee
        return null
    }

    override fun createTable(name: String) {}
    override fun getSQLDate(cursor: Cursor) {}
    override fun saveDate(workSpaceName: String) {}
    override fun getListDate(articles: HashMap<String, Item>) {}
    override fun getAllItems(): Array<Item> = items
}