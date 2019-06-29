package com.example.administrator.listener.Search

import com.example.administrator.character.Character
import kotlin.reflect.KClass

class CharacterSear<T: Character>(val type: KClass<T>): Search<T>() {
    override fun search(character: Character): List<T> = Character.characters.values.filter { it.x_coordinate == character.x_coordinate&&it::class == type&&it != character } as List<T>

    companion object{
        val NPCSear: CharacterSear<Character> = CharacterSear(Character::class)
    }
}