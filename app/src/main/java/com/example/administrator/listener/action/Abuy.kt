package com.example.administrator.listener.action

import com.example.administrator.buildings.GameUI
import com.example.administrator.character.Player
import com.example.administrator.item.Item
import com.example.administrator.item.SellItem
import com.example.administrator.listener.action.Action
import com.example.administrator.utils.Response

class Abuy: Action<SellItem>() {
    override fun listener(ui: GameUI, adapter: SellItem) {
        val response = object : Response<Int>(){
            override fun doThings(amount: Int) {
                if (amount > 0&&amount<=adapter.total&&Player.playerDate.money>amount*adapter.originalPrice) {
                    adapter.total = adapter.total - amount
                    Player.playerDate.money -= adapter.total*adapter.originalPrice
                    val product = adapter.item
                    val item = product.getListItem<Item>(product.name)
                    adapter.total -= amount
                    product.total -= amount
                    item.total = amount
                    item.workSpace = Player.playerName
                    Item.addItem(item, Player.bag)
                }
            }
        }
        ui.reText<Int>("Enter the number of buy", response)
    }

}