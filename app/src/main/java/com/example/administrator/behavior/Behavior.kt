package com.example.administrator.behavior

import com.example.administrator.character.Character

abstract class Behavior{

    abstract fun use(character: Character): Boolean
    abstract fun receive(character: Character): Boolean

}