package com.example.administrator.behavior

import com.example.administrator.character.Character

abstract class Behavior{
/**
 * @author:YouerSu
 * */

    abstract fun use(character: Character): Boolean/**@return: To tell caller weather NPC  use this behavior or not */

    abstract fun receive(character: Character): Boolean/**@return: To tell caller if NPC  can use this behavior */

}