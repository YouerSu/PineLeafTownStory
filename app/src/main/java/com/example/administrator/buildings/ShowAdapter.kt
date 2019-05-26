package com.example.administrator.buildings

interface ShowAdapter {

    var click: (GameUI, ShowAdapter) -> Unit

    fun UIPageAdapter(): Map<String, String>

    fun onClick(gameUI: GameUI)

}
