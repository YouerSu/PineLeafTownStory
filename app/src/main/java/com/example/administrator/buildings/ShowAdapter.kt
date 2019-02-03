package com.example.administrator.buildings

import com.example.administrator.listener.Listener

interface ShowAdapter {

    var click: (GameUI, ShowAdapter) -> Unit

    fun UIPageAdapter(): Map<String, String>

    fun onClick(gameUI: GameUI)

}
