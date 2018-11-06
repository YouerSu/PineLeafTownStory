package com.example.administrator.item

import android.database.Cursor
import com.example.administrator.behavior.Behavior
import com.example.administrator.character.Character
import com.example.administrator.utils.Info
import com.example.administrator.utils.Tools
import com.example.administrator.utils.Tools.getType
import java.util.*

open
class Tool(private var behavior: Behavior, name: String, volume: Int, originalPrice: Int) : Item(name, volume, originalPrice) {
    val items: Array<Item> = arrayOf(

    )

    fun use(character: Character): Boolean=behavior.use(character)
    override fun haveTable()=true

    fun receive(character: Character): Boolean = behavior.receive(character)

    override fun createTable(name: String) {}
    override fun getSQLDate(cursor: Cursor) {
        behavior = getType(cursor.getString(cursor.getColumnIndex(Info.BEHAVIOR)))
    }
    override fun saveDate(workSpaceName: String) {}
    override fun getListDate(articles: HashMap<String, Item>) {}
    override fun getAllItems(): Array<Item> = items

}