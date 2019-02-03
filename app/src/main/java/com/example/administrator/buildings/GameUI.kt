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

        fun getAdapterMap(name: String, l1: String, l2: String, l3: String): Map<String, String> {
            val item = HashMap<String, String>()
            item[Info.NAME] = name
            item[Info.LT1] = l1
            item[Info.LT2] = l2
            item[Info.LT3] = l3
            return item
        }
    }
}