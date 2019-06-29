package com.example.administrator.listener.Search

import com.example.administrator.character.Character
import com.example.administrator.character.Player
import com.example.administrator.item.Item
import com.example.administrator.utils.Tools

class BagSear: Search<Item>() {
    override fun search(character: Character): List<Item> = Tools.toList<Item>(Player.bag.values)
}