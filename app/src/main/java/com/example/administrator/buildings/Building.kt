package com.example.administrator.buildings

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import com.example.administrator.character.Employee
import com.example.administrator.item.Item
import com.example.administrator.item.Tool
import com.example.administrator.utils.Info
import com.example.administrator.utils.Sql

import android.content.ContentValues.TAG
import java.util.*

class Building(var name: String, var capacity: Int, var master: String?){
    var items = HashMap<String, Item>()
    val employees = ArrayList<Employee>()

    init {
        buildings.add(this)
    }

    private fun saveBuildingDate() {
        Log.i(TAG, "saveBuildingDate: count")
        createNewBuilding(Sql.getDateBase())
        for (item in items.values)
            item.saveIndexDate(name)
    }

    fun addEmployee(employee: Employee) {
        employee.workSpace = name
        employees.add(employee)
    }

    fun findWorker(item: Item): Employee? {
        for (employee in employees)
            if (employee.receive(item)) return employee
        return null
    }

    private fun createNewBuilding(db: SQLiteDatabase) {
        Item.createIndex(name)
        db.execSQL("insert into " + Info.BUILDING + " (" + Info.NAME + "," + Info.MASTER + "," + Info.capacity + ") values ('" + name + "','" + master + "'," + capacity + ")")
    }

    fun services(): List<Tool> {
        val list = ArrayList<Tool>()
        for (item in items.values)
            if (item is Tool)
                list.add(item)
        return list
    }

    companion object {
        var buildings = LinkedList<Building>()

        fun saveDate() {
            Sql.operating(arrayOf("DELETE FROM " + Info.BUILDING))
            for (building in getBuildings())
                building.saveBuildingDate()
        }

        fun findWorkSpace(workSpace: String): Building{
           for (building in buildings)
               if (building.name == workSpace) return building
            error("$workSpace No Found")
        }

        fun getDate() {
            val iDate = Sql.getAllInfo(Info.BUILDING) ?: return
            while (iDate.moveToNext()) {
                Log.i(TAG, "getBuildingDate: count")
                val building = Building(iDate.getString(iDate.getColumnIndex(Info.NAME)), iDate.getInt(iDate.getColumnIndex(Info.capacity)), iDate.getString(iDate.getColumnIndex(Info.MASTER)))
                building.items = Item.getIndexDate(building.name)
            }
            iDate.close()
        }

        fun getWhere(x: Int): Building {
            return buildings.get(x)
        }

        fun getBuildings(): List<Building> {
            return buildings
        }
    }
}
