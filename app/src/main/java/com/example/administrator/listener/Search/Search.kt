package com.example.administrator.listener.Search

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Character

abstract class Search<T: ShowAdapter> {
    abstract fun search(character: Character): List<T>
    fun show(list: List<T>,ui: GameUI){
        ui.showListDialogue(list)
    }
}