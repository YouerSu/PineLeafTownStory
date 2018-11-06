package com.example.administrator.behavior

import com.example.administrator.buildings.Building
import com.example.administrator.character.Character
import java.util.*

class Move :Behavior() {

    override fun use(character: Character): Boolean {
        character.x_coordinate=Random().nextInt(Building.getBuildings().size)
        return true
    }

    override fun receive(character: Character): Boolean =true
}