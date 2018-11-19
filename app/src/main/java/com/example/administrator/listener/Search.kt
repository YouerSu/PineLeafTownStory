package com.example.administrator.listener

import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Character

abstract class Search<T> {
    abstract fun search(character: Character): Array<T>
    fun show(array: Array<ShowAdapter>,ui: GameUI){
        ui.showListDialogue(array.toList())
    }
}