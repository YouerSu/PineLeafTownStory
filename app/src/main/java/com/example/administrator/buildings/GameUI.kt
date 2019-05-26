package com.example.administrator.buildings

import com.example.administrator.utils.Info
import com.example.administrator.utils.Response

import java.util.HashMap

interface GameUI {

    fun refreshUI()

    fun <T> reText(message: String, text: Response<T>)

    fun dialogueBox(message: String)

    fun <T> chooseDialogue(message: String, messages: Array<T>, choose: Response<T>)

    fun <T : ShowAdapter> showListDialogue(items: List<T>)

    fun dayChange()

    fun run(runnable: Runnable)

    companion object {

        fun getAdapterMap(name: String, info: String): Map<String, String> {
            val item = HashMap<String, String>()
            item[Info.NAME] = name
            item[Info.LT1] = info
            return item
        }
    }
}