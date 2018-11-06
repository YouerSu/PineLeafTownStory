package com.example.administrator.behavior

import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Character
import com.example.administrator.character.Player
import com.example.administrator.item.Item
import com.example.administrator.utils.Tools

//查找物 监听
abstract class Search: Behavior() {
    //TODO:为查找到的物品添加监听

    abstract fun listener(ui: GameUI,adapter: ShowAdapter)
    abstract fun accept(adapter: ShowAdapter):Boolean

//    override fun use(character: Character): Boolean {
//        val type = Tools.getType<ShowAdapter>(type)
//        if (type is Character)
//        val h = Search()
//        Character.characters.filter { it.x_coordinate==character.x_coordinate }.map { it.listener.listener=h::listener }
//        else if (type is Item)
//            Building.getWhere(character.x_coordinate).items.values.filter { receive(it) }.map { it.listener.listener=listener }
//        return true
//    }

    override fun receive(character: Character): Boolean = character is Player
}