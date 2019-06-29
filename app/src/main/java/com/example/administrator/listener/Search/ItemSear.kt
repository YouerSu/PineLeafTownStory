package com.example.administrator.listener.Search

import com.example.administrator.buildings.Building
import com.example.administrator.character.Character
import com.example.administrator.item.Item
import com.example.administrator.item.SellItem
import com.example.administrator.item.Tool
import kotlin.reflect.KClass

class ItemSear<T: Item>(val type: KClass<T>): Search<T>(){
    override fun search(character: Character): List<T> = Building.getWhere(character.x_coordinate).items.values.filter { it::class == type } as List<T>

    companion object{
        val sellItemSear = ItemSear(SellItem::class)
        val toolSear = ItemSear(Tool::class)
    }
}