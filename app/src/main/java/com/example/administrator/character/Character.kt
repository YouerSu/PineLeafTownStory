package com.example.administrator.character

import android.util.Log
import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameUI
import com.example.administrator.utils.Info
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.utils.Sql
import com.example.administrator.utils.Tools

import android.content.ContentValues.TAG

abstract class Character : ShowAdapter, NPC {
    lateinit var name: String
    var money: Int = 0
    var prestige: Int = 0
    var x_coordinate: Int = 0
    var salary: Int = 0
    var workSpace: String = "救济所"
    override lateinit var click: (GameUI, ShowAdapter) -> Unit

    abstract fun init()

    override fun behavior() {
        for (tool in Building.getWhere(x_coordinate).services())
            if (tool.receive(this) && tool.use(this)) break
    }


    override fun UIPageAdapter(): Map<String, String> {
        return GameUI.getAdapterMap(name, "薪水：$salary", "工作地点：$workSpace", "职业：${Tools.getSuffix(javaClass.name)}")
    }

    override fun onClick(gameUI: GameUI) {
        click(gameUI, this)
    }

    protected open fun saveCharacterDate() {
        Log.i(TAG, "saveCharacterDate: count")
        Sql.operating(arrayOf("update " + Info.CHARACTER + " set " + Info.id + " = '" + javaClass.name + "'," + Info.MONEY + " = " + money + "," + Info.PRESTIGE + " = " + prestige + "," + Info.coordinate + " = " + x_coordinate + "," + Info.salary + " = " + salary + "," + Info.MASTER + " = '" + workSpace + "' where " + Info.NAME + " = '" + name + "'"))
    }

    fun wages() {
        val masterName = Building.findWorkSpace(workSpace).master
        val master = characters[masterName]
        master?.let { it.money -= salary }
        money += salary
    }

    companion object {
        var characters = HashMap<String,Character>()

        fun getDate() {
            val iDate = Sql.getDateBase().rawQuery("select * from " + Info.CHARACTER, null)
            while (iDate.moveToNext()) {
                val character = Tools.getType<Character>(iDate.getString(iDate.getColumnIndex(Info.id)))
                character.name = iDate.getString(iDate.getColumnIndex(Info.NAME))
                character.money = iDate.getInt(iDate.getColumnIndex(Info.MONEY))
                character.prestige = iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE))
                character.x_coordinate = iDate.getInt(iDate.getColumnIndex(Info.coordinate))
                character.workSpace = iDate.getString(iDate.getColumnIndex(Info.MASTER))
                character.salary = iDate.getInt(iDate.getColumnIndex(Info.salary))
                character.init()
                characters[character.name] = character
            }
            iDate.close()
        }


        fun saveDate() {
            for (character in characters.values)
                character.saveCharacterDate()
        }

        fun createNewCharacter(className: String, name: String, money: Int, prestige: Int, x_coordinate: Int, salary: Int, workSpace: String) {
            if (Tools.getType<Any>(className) !is Character) return
            Sql.getDateBase().execSQL("insert into " + Info.CHARACTER + " (" + Info.id + "," + Info.NAME + "," + Info.MONEY + "," + Info.PRESTIGE + "," + Info.coordinate + "," + Info.salary + "," + Info.MASTER + ") values ('" + className + "','" + name + "'," + money + "," + prestige + "," + x_coordinate + "," + salary + ",'" + workSpace + "')")
            val character = Tools.getType<Character>(className)
            character.name = name
            character.money = money
            character.prestige = prestige
            character.x_coordinate = x_coordinate
            character.salary = salary
            character.workSpace = workSpace
            characters[character.name] = character
        }
    }
}