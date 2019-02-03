package com.example.administrator

import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameTime
import com.example.administrator.buildings.GameUI
import com.example.administrator.character.Player
import com.example.administrator.character.StoresEmployee
import com.example.administrator.item.Item
import com.example.administrator.item.Mall
import com.example.administrator.character.Character

object Init {
    fun init(game: GameUI) {
        Init.getAllDate()
        createPlayerDate(game)
        GameTime.setTimeDate(game)
    }

    private fun getAllDate() {
        Building.getDate()
        Character.getDate()
    }

    fun saveAllDate() {
        GameTime.timeDate.saveDate()
        Building.saveDate()
        Character.saveDate()
    }

    private fun createPlayerDate(gameUI: GameUI) {
        if (Character.characters.size==0) {
            Player.createDate(gameUI)
            Init.firstPlayGame()
            //Init.createPlayerDate(gameUI);
        }
    }

    private fun firstPlayGame() {
        Character.createNewCharacter(StoresEmployee::class.java.name, "蚴牙", 1000, 200, 0, 8000, "杂货铺")
        Building("杂货铺", 0, "蚴牙").items = Item.changeToMap(Mall.items as Array<Item>)
        Building("救济所",100,"蚴牙")
    }

}
