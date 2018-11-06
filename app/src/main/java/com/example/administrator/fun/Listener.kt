package com.example.administrator.`fun`

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter

class Listener(var listener:(GameUI,ShowAdapter) -> Unit) {
    fun onClick(gameUI: GameUI,item :ShowAdapter) {
        listener(gameUI,item)
    }
}