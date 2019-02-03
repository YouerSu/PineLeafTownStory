package com.example.administrator.character


import com.example.administrator.item.Item
import com.example.administrator.item.SellItem
import com.example.administrator.buildings.Building
import com.example.administrator.utils.Tools

class StoresEmployee : Employee() {



    override fun work(item: Item,customer: Character){
        if (item !is SellItem) return
        item.total = item.total - 1
        val workSpace = Building.findWorkSpace(master)
        val master =  Character.characters[workSpace.master]
        master?.let { it.money += item.sellPrice }
        customer.money-=item.sellPrice
    }

    override fun receive(item: Item): Boolean = item is SellItem

}
