package com.example.administrator.listener.action

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter

//查找物 监听
abstract class Action<T: ShowAdapter>{
    //TODO:为查找到的物品添加监听
    abstract fun listener(ui: GameUI,adapter: T): Unit

//    override fun use(character: Character): Boolean {
//        return true
//    }
}

class NonAction: Action<ShowAdapter>() {
    override fun listener(ui: GameUI, adapter: ShowAdapter) {
        //Do nothing
    }
}