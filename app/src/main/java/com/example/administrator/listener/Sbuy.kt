package com.example.administrator.listener

import com.example.administrator.buildings.Building
import com.example.administrator.character.Character
import com.example.administrator.item.SellItem

class Sbuy: Search<SellItem>() {
    override fun search(character: Character): Array<SellItem> =Building.getWhere(character.x_coordinate).items.values.filter { it is SellItem }.toTypedArray() as Array<SellItem>
}