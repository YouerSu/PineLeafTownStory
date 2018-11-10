package com.example.administrator.listener

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter

class Listener<T:ShowAdapter>(var listener:(GameUI,T) -> Unit) {
    fun onClick(gameUI: GameUI,item :T) {
        listener(gameUI,item)
    }
}