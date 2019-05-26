package com.example.administrator.listener.Search

import com.example.administrator.character.Character

class CharacterSear<T: Character>(val type: String): Search<T>() {
    override fun search(character: Character): List<T> = Character.characters.values.filter { it.x_coordinate == character.x_coordinate&&it.javaClass.name == type&&it != character } as List<T>

    companion object{
        val NPCSear: CharacterSear<Character> = CharacterSear(Character.toString())
    }
}