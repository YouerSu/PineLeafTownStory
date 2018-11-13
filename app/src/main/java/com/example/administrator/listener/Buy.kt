package com.example.administrator.listener

import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameUI
import com.example.administrator.character.Player
import com.example.administrator.item.Item
import com.example.administrator.item.SellItem
import com.example.administrator.utils.Response

class Buy: Search<SellItem>() {
    override fun listener(ui: GameUI, adapter: SellItem) {
        val response = object : Response<Int>(){
            override fun doThings(amount: Int) {
                if (amount > 0&&amount<=adapter.total&&Player.player.money>amount*adapter.originalPrice) {
                    adapter.total = adapter.total - amount
                    Player.player.money -= adapter.total*adapter.originalPrice
                    val product = adapter.item
                    val item = product.getListItem<Item>(product.name)
                    adapter.total -= amount
                    product.total -= amount
                    item.total = amount
                    item.master = Player.getPlayerName()
                    Item.addItem(item, Player.bag)
                }
            }
        }
        ui.reText<Int>("Enter the number of buy", response)
    }

    override fun use(player: Player,ui: GameUI) {
        Building.getWhere(player.x_coordinate).items.values.filter { it is SellItem }.map { it.listener.listener=this::listener }
    }
}