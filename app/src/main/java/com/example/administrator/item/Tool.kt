package com.example.administrator.item

import android.database.Cursor
import com.example.administrator.behavior.Behavior
import com.example.administrator.character.Character
import java.util.*

open
class Tool(private val behavior: Behavior, name: String, volume: Int, originalPrice: Int) : Item(name, volume, originalPrice) {
    val items: Array<Item> = arrayOf(

    )

    fun use(character: Character): Boolean=behavior.use(character)
    override fun haveTable()=true

    fun receive(character: Character): Boolean = behavior.receive(character)

    override fun createTable(name: String) {}
    override fun getSQLDate(cursor: Cursor) {}
    override fun saveDate(workSpaceName: String) {}
    override fun getListDate(articles: HashMap<String, Item>) {}
    override fun getAllItems(): Array<Item> = items

}