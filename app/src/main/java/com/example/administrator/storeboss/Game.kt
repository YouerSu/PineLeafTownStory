package com.example.administrator.storeboss

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast

import com.example.administrator.buildings.Building
import com.example.administrator.buildings.GameUI
import com.example.administrator.buildings.ShowAdapter
import com.example.administrator.character.Character
import com.example.administrator.character.Player
import com.example.administrator.item.Item
import com.example.administrator.utils.Info
import com.example.administrator.utils.MyPagerAdapter
import com.example.administrator.utils.Response
import com.example.administrator.utils.Sql
import com.example.administrator.utils.Tools

import java.util.ArrayList

class Game : AppCompatActivity(), GameUI {

    private var pager: ViewPager? = null
    private var timeView: TextView? = null
    private var playerView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Sql.setInfo(Sql(this))
        Character.getAllDate()
        Player.createPlayerDate(this)
        timeView = findViewById(R.id.clock)
        playerView = findViewById(R.id.playerDate)
        findViewById<View>(R.id.showCharacter).setOnClickListener { view -> showListDialogue(Character.getCharacters().filter { it.x_coordinate == Player.getX() }) }
        findViewById<View>(R.id.showBag).setOnClickListener { view -> showListDialogue<Item>(Tools.toList<Item>(Player.bag.values)) }
        findViewById<View>(R.id.showItem).setOnClickListener { view -> showListDialogue<Item>(Tools.toList<Item>(Building.getBuildings()[pager!!.currentItem].items.values)) }
        setBuilding()
        setText()
    }

    private fun <T : ShowAdapter>  changeList(items: List<T>): ListView {
        //接收参数，转化参数，展示参数
        val listItem = ArrayList<Map<String, String>>()
        for (item in items)
            listItem.add(item.UIPageAdapter())
        return getListView(listItem)
    }

    fun getListView(listItem: List<Map<String, String>>): ListView {
        val sa = SimpleAdapter(this, listItem, R.layout.item_list, arrayOf(Info.NAME, Info.LT1, Info.LT2, Info.LT3), intArrayOf(R.id.name, R.id.lt1, R.id.lt2, R.id.lt3))
        val alertDialog = getDialog(R.layout.show_list)
        val list = alertDialog.window!!.findViewById<ListView>(R.id.list)
        list.adapter = sa
        return list
    }

    override fun <T : ShowAdapter> showListDialogue(items: List<T>) {
        val UI = this
        changeList(items).setOnItemClickListener { adapterView, view, i, l -> items[i].onClick(UI) }
    }


    override fun <T> chooseDialogue(message: String, messages: Array<T>, response: Response<T>) {
        val builder = AlertDialog.Builder(this)
                .setTitle(message)
                .setItems(messages.clone() as Array<String>) { dialog, which -> response.doThings(messages[which]) }
        builder.create().show()
    }

    override fun dayChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setBuilding() {
        for (building in Building.getBuildings())
            showBuilding(building.name, R.layout.building)
    }

    override fun <T> reText(message: String, response: Response<T>) {
        //强行转换如同虚设XD
        val alertDialog = getInputDialog(message)
        alertDialog.findViewById<View>(R.id.ok).setOnClickListener { view ->
            try {
                response.doThings((alertDialog.findViewById<View>(R.id.text) as EditText).text.toString() as T)
                alertDialog.dismiss()
            } catch (e: ClassCastException) {
                Toast.makeText(this, "您输入的字符不符合格式", Toast.LENGTH_SHORT).show()
                reText(message,response)
            }
        }
    }


    override fun dialogueBox(message: String) {
        val alertDialog = getDialog(R.layout.dialoguebox)
        val window = alertDialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)
        //对话框
        val next = window.findViewById<TextView>(R.id.message)
        val ima = window.findViewById<ImageView>(R.id.ima)
        when (message.substring(0, message.indexOf(":"))) {
            "banker" -> ima.setImageResource(R.mipmap.banker)
            "dd" -> ima.setImageResource(R.drawable.ic_launcher_background)
            "ss" -> ima.setImageResource(R.drawable.ic_launcher_background)
            else -> ima.setImageResource(R.mipmap.player)
        }
        next.text = message
        next.setOnClickListener { view: View -> alertDialog.dismiss() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val action = object : Response<String>() {
            override fun doThings(result:String) {
                if (result == "离开") {
                    saveDate()
                    finish()
                }
            }
        }
        chooseDialogue<String>("你...确定要离去吗？", arrayOf("离开", "留下"), action)
        return true
    }


    fun showBuilding(name: String, layout: Int) {
        titleList.add(name)
        val view = View.inflate(this, layout, null)
        pagerList.add(view)
    }

    private fun getInputDialog(text: String): AlertDialog {
        val alertDialog = getDialog(R.layout.input_dialogue)
        val window = alertDialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        val editText = window.findViewById<EditText>(R.id.text)
        val ss = SpannableString(text)
        val ass = AbsoluteSizeSpan(15, true)
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        editText.hint = ss
        return alertDialog
    }

    fun setText() {
        //加载适配器
        pager = findViewById(R.id.pager)
        val adapter = MyPagerAdapter(pagerList, titleList)
        pager!!.adapter = adapter
        pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                Player.getPlayerDate().x_coordinate = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun getDialog(layout: Int): AlertDialog {
        //创建Dialog
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.show()
        alertDialog.setContentView(layout)
        return alertDialog
    }

    private fun createBuilding() {

        if (Player.getPlayerDate().money >= Info.BUILDING_PRICE) {
            Player.getPlayerDate().money = Player.getPlayerDate().money - Info.BUILDING_PRICE
            Building("建筑", 1, Player.getPlayerDate().name)
            dialogueBox("ada:OK")
        } else
            Toast.makeText(this, "你没有足够的金钱", Toast.LENGTH_SHORT).show()
    }


    override fun refreshUI() {
        var season: String? = null
        when (Player.timeDate.month) {
            1 -> season = "春季日"
            2 -> season = "夏季日"
            3 -> season = "秋季日"
            4 -> season = "冬季日"
        }
        timeView?.text = String.format("%s第%d天  %d:%02d", season, Player.timeDate.day, Player.timeDate.hour, Player.timeDate.minute)
        playerView?.text = String.format("云团:%d   声望:%d", Player.getPlayerDate().money, Player.getPlayerDate().prestige)
    }

    companion object {
        private val pagerList = ArrayList<View>()
        private val titleList = ArrayList<String>()

        private fun saveDate() {
            Character.saveAllDate()
        }
    }

    override fun run(runnable: Runnable?) = runOnUiThread(runnable)

}