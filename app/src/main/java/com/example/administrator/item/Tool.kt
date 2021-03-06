package com.example.administrator.item

import android.database.Cursor
import com.example.administrator.behavior.Behavior
import com.example.administrator.behavior.BuyBeh
import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Character
import com.example.administrator.character.Player
import com.example.administrator.listener.Listener
import com.example.administrator.utils.Info
import com.example.administrator.utils.Sql
import com.example.administrator.utils.Tools
import com.example.administrator.utils.Tools.getType

open
class Tool: Item {
    lateinit var behavior: Behavior
    lateinit var listen: Listener<ShowAdapter>
    constructor(behavior: Behavior, listen: Listener<ShowAdapter>, name: String, volume: Int, originalPrice: Int) : super(name, volume, originalPrice){
        this.behavior = behavior
        this.listen = listen
    }
    constructor() : super()

    fun use(player: Player,ui: GameUI) =  listen.use(player,ui)
    fun use(character: Character): Boolean = behavior.use(character)

    override fun haveTable()= false

    fun receive(character: Character): Boolean = behavior.receive(character)

    override fun createTable(name: String) {
        Sql.operating(arrayOf(
            "create table if not exists " + name + Tools.getSuffix(javaClass.name) + "(" + Info.BEHAVIOR + " text)",
            "DELETE FROM " + name + "Tool"
                                                    )
        )
    }
    override fun getSQLDate(cursor: Cursor) {
        behavior = getType(cursor.getString(cursor.getColumnIndex(Info.BEHAVIOR)))
    }
    override fun saveDate(workSpaceName: String) {
        Sql.operating(arrayOf(
            "insert into " + workSpaceName + Tools.getSuffix(javaClass.name) + " (" + Info.BEHAVIOR + ") values(" + behavior.javaClass.name + ")"
                                                    )
        )
    }

    override fun <T : Item> getListItem(name: String): T =  changeToMap(items)[name] as T

    companion object {
        val items: Array<Tool> = arrayOf(
                Tool(BuyBeh(), Listener.BuyLis as Listener<ShowAdapter>, "售货台", 20, 100)

        )
    }

}