package com.example.administrator.listener

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Player

//查找物 监听
abstract class Search<T:ShowAdapter>{
    //TODO:为查找到的物品添加监听

    abstract fun listener(ui: GameUI,adapter: T)
    abstract fun use(player: Player,ui: GameUI)
//    override fun use(character: Character): Boolean {
//        return true
//    }

}