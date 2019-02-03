package com.example.administrator.character

import com.example.administrator.buildings.GameUI
import com.example.administrator.item.Item
import com.example.administrator.utils.Info
import com.example.administrator.utils.Response
import com.example.administrator.utils.Sql

import java.util.HashMap

class Player : Character() {

    override fun init() {
        playerDate = this
        getBagDate()
    }


    private fun getBagDate() {
        bag = Item.getIndexDate(name!!)
    }


    public override fun saveCharacterDate() {
        super.saveCharacterDate()
        for (item in bag.values)
            item.saveIndexDate(name!!)
    }

    companion object {

        lateinit var bag: HashMap<String, Item>
        lateinit var playerDate: Player

        fun createDate(gameUI: GameUI) {
            playerDate = Player()
            val finalCharacter = playerDate
            gameUI.reText("输入你的名字", object : Response<String>() {
                //TODO:判断是否重复
                override fun doThings(name: String) {
                    finalCharacter.setName(name)
                    Sql.operating(arrayOf("insert into " + Info.CHARACTER + " (" + Info.id + "," + Info.NAME + "," + Info.MONEY + "," + Info.PRESTIGE + "," + Info.coordinate + "," + Info.salary + "," + Info.MASTER + ") values ('" + Player::class.java.name + "','" + name + "',0,0,0,3000,'" + null + "')", "create table if not exists " + name + Info.INDEX + " (" + Info.id + " text," + Info.NAME + " text," + Info.total + " integer)"))
                    finalCharacter.init()
                    characters[name] = finalCharacter
                }
            })
        }


        val playerName: String
            get() = playerDate.name
        var x: Int
            get() = playerDate.x_coordinate
            set(x) {
                playerDate.x_coordinate = x
            }
    }

}