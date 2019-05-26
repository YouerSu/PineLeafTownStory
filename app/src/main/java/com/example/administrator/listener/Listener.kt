package com.example.administrator.listener

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Player
import com.example.administrator.item.SellItem
import com.example.administrator.listener.Search.ItemSear
import com.example.administrator.listener.Search.Search
import com.example.administrator.listener.action.Abuy
import com.example.administrator.listener.action.Action

class Listener<T: ShowAdapter>(val action: Action<T>, val search: Search<T>) {
    fun use(player: Player, ui: GameUI){
        val items =search.search(player)
        items.forEach { it.click = action::listener as (GameUI, ShowAdapter) -> Unit }
        search.show(items,ui)
    }

    companion object{
        val BuyLis: Listener<SellItem> = Listener(Abuy(), ItemSear.sellItemSear)
    }

}