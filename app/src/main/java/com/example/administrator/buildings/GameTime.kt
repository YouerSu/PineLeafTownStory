package com.example.administrator.buildings

import com.example.administrator.character.Character
import com.example.administrator.utils.Info
import com.example.administrator.utils.Sql

import java.util.Timer
import java.util.TimerTask

val SPEED: Long = 10000//时间流逝速度

class GameTime(private val gameUI: GameUI) : TimerTask() {
    //各类时间事件
    var minute: Int = 0
    var hour: Int = 0
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0


    fun saveDate() {
        Sql.operating(arrayOf("update " + Info.TIME + " set " + Info.MINUTE + " = " + minute + "," + Info.HOUR + " = " + hour + "," + Info.DAY + " = " + day + "," + Info.MONTH + " = " + month + "," + Info.YEAR + " = " + year))
    }

    fun getTimeDate() {
        val iDate = Sql.getAllInfo(Info.TIME)
        if (iDate != null)
            while (iDate.moveToNext()) {
                //事实证明Cursor的指针是从第一条数据的前一个开始的
                minute = iDate.getInt(iDate.getColumnIndex(Info.MINUTE))
                hour = iDate.getInt(iDate.getColumnIndex(Info.HOUR))
                day = iDate.getInt(iDate.getColumnIndex(Info.DAY))
                month = iDate.getInt(iDate.getColumnIndex(Info.MONTH))
                year = iDate.getInt(iDate.getColumnIndex(Info.YEAR))
            }
    }


    override fun run() {
        minute += SPEED.toInt() / 1000
        if (minute >= 60) {
            minute = 0
            hour += 1
            if (hour >= 24) {
                hour = 7
                day += 1
                gameUI.dayChange()
                if (day > 25) {
                    day = 1
                    month += 1
                    for (character in Character.characters.values)
                        character.wages()
                    if (month > 4) {
                        month = 1
                        year += 1
                    }
                }
            }
        }
        gameUI.refreshUI()
    }

    companion object {

        lateinit var timeDate: GameTime

        fun setTimeDate(gameUI: GameUI) {
            timeDate = GameTime(gameUI)
            timeDate.getTimeDate()
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    gameUI.run(timeDate)
                }
            }, 800L,SPEED)
        }
    }

}