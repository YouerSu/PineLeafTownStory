package com.example.administrator.listener.Search

import com.example.administrator.buildings.Building
import com.example.administrator.character.Character
import com.example.administrator.item.Item
import com.example.administrator.item.SellItem
import com.example.administrator.item.Tool

class ItemSear<T: Item>(val type: Class<T>): Search<T>(){
    override fun search(character: Character): List<T> = Building.getWhere(character.x_coordinate).items.values.filter { it::class.java == type } as List<T>

    companion object{
        val sellItemSear = ItemSear(SellItem::class.java)
        val toolSear = ItemSear(Tool::class.java)
    }
}