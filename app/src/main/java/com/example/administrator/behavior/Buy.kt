package com.example.administrator.behavior

import com.example.administrator.buildings.Building
import com.example.administrator.character.Character
import com.example.administrator.item.SellItem

class Buy :Behavior(){
    override fun use(character: Character): Boolean {

        val place = Building.getWhere(character.x_coordinate)
        for (item in place.items){
            if (item is SellItem && isCheap(item,character.money)){
                val worker = place.findWorker(item)
                worker?.work(item,character)
                return worker!=null
            }
        }
        return false
    }

    override fun receive(character: Character): Boolean = true

    private fun isCheap(item: SellItem,balance: Int): Boolean {
        val price = item.sellPrice
        val popular = item.popular
        val value = item.originalPrice
        return balance>=price&&price - popular <= value
    }

}