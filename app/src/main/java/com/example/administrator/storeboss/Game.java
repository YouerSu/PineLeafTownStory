package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Player;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.ShowAdapter;
import com.example.administrator.utils.Sql;
import com.example.administrator.buildings.Character;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends AppCompatActivity implements GameUI{
    private static ViewPager pager;
    private static List<View> pagerList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();
    static TextView timeView;
    static TextView playerView;
    private static long mExitTime;
    boolean ok = false;
    boolean choose = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Sql.info = Db.setInfo(this);
        getDefaultBuilding();
        setText();
        //时间流逝
        timeView = findViewById(R.id.clock);
        playerView = findViewById(R.id.player);
    }

    public ListView showListDialogue(final List<ShowAdapter> items){
        //接收参数，转化参数，展示参数
        List<Map<String,String>> listItem = new ArrayList<>();
        for (ShowAdapter item:items)
            listItem.add(item.UIPageAdapter());
        SimpleAdapter sa = new SimpleAdapter(this,listItem,R.layout.item_list,new String[]{Info.NAME,Info.LT1,Info.LT2,Info.LT3},new int[]{R.id.name,R.id.lt1,R.id.lt2,R.id.lt3});
        ListView list = findViewById(R.id.stockList);
        list.setAdapter(sa);
        return list;
    }

    @Override
    public void showMyOwnListDialogue(final List<ShowAdapter> items) {
        final GameUI UI = this;
        showListDialogue(items).setOnItemClickListener((adapterView, view, i, l) -> items.get(i).showMyOwnOnClick(UI));

    }

    @Override
    public void showNotMyOwnListDialogue(final List<ShowAdapter> items) {
        final GameUI UI = this;
        showListDialogue(items).setOnItemClickListener((adapterView, view, i, l) -> items.get(i).showNotMyOwnOnClick(UI));
    }

    @Override
    public boolean trueOrFalseDialogue(String message) {
        AlertDialog alertDialog = getDialog(R.layout.crystal);
        alertDialog.setTitle(message);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "是", (dialogInterface, i) -> {
            choose = true;
            ok();
        });
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "否", (dialogInterface, i) -> {
            choose = false;
            ok();
        });

        waitOk();
        return choose;
    }

    public void setBuiling(){
        for (Building building: Building.getBuildings()){
        showBuilding(building.getName(),R.layout.building);
        }
    }

    public void dayHarvest() {
        //昼夜交替,显示一天的收获

    }

    @Override
    public String reName(String messages) {
        EditText editText = getEditText(messages);
        waitOk();
        return editText.getText().toString();
    }

    public EditText getEditText(String messages) {
        final AlertDialog alertDialog = getInputDialog(messages);
        Window window = alertDialog.getWindow();
        final EditText editText = window.findViewById(R.id.tname);
        window.findViewById(R.id.ok).setOnClickListener(view -> {
            ok();
            alertDialog.dismiss();
        });
        return editText;
    }

    @Override
    public int reAmount(String message) {
        EditText editText = getEditText(message);
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        waitOk();
        return Integer.valueOf(editText.getText().toString());
    }

    private void ok() {
        ok = true;
    }

    private void waitOk() {
        while (!ok) ;
        ok = false;
    }

    @Override
    public void dialogueBox(String message) {
        final AlertDialog alertDialog = getDialog(R.layout.crystal);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        TextView next;
        if (message.substring(0,message.indexOf(":")).equals(Player.getPlayerName())) {
            //玩家对话框
            next = window.findViewById(R.id.pmessage);
            ImageView npc = window.findViewById(R.id.player);
            npc.setImageResource(R.drawable.ic_launcher_background);
        } else {
            //NPC对话框
            next = window.findViewById(R.id.message);
            ImageView npc = window.findViewById(R.id.NPC);
            switch (message.substring(0,message.indexOf(":"))) {
                case "banker":
                    npc.setImageResource(R.mipmap.banker);
                    break;
                case "dd":
                    npc.setImageResource(R.drawable.ic_launcher_background);
                    break;
                case "ss":
                    npc.setImageResource(R.drawable.ic_launcher_background);
                    break;
                default:
                    npc.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        next.setText(message);
        next.setOnClickListener((View view)-> alertDialog.dismiss());

    }

    @Override
    protected void onDestroy() {
        saveDate();
        super.onDestroy();
    }

    private static void saveDate(){
        Character.saveAllDate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                Toast.makeText(this, "你...确定要离去吗?", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                saveDate();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getDefaultBuilding() {
        showBuilding("建筑小屋",R.layout.workshop);
        showBuilding("银行",R.layout.bank);
        showBuilding("工厂",R.layout.factory);
        showBuilding("洞窟",R.layout.cave);
    }

    public void showBuilding(String s, int a){
        titleList.add(s);
        View view = View.inflate(this, a, null);
        pagerList.add(view);
    }

    private AlertDialog getInputDialog(String text) {
        AlertDialog alertDialog = getDialog(R.layout.textin);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        final EditText editText = window.findViewById(R.id.tname);
        SpannableString ss = new SpannableString(text);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);
        ss.setSpan(ass,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(ss);
        return alertDialog;
    }

    public void setText() {
        //加载适配器
        pager = findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(pagerList, titleList);
        pager.setAdapter(adapter);
    }

    private AlertDialog getDialog(int layout) {
        //创建Dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
        alertDialog.show();
        alertDialog.setContentView(layout);
        return alertDialog;
    }

    private void createBuilding() {

        if (Player.getPlayerDate().getMoney()>= Info.BUILDING_PRICE) {
            Player.getPlayerDate().setMoney(Player.getPlayerDate().getMoney()-Info.BUILDING_PRICE);
            new  Building("建筑",1, Player.getPlayerDate().getName());
            dialogueBox("ada:OK");
        } else
            Toast.makeText(this, "你没有足够的金钱", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void refreshUI() {
        String season = null;
        switch (Player.getPlayerDate().timeDate.getMonth()) {
            case 1:
                season = "春季日";
                break;
            case 2:
                season = "夏季日";
                break;
            case 3:
                season = "秋季日";
                break;
            case 4:
                season = "冬季日";
        }
    timeView.setText(season + "第" + Player.getPlayerDate().timeDate.getDay() + "天  " + Player.getPlayerDate().timeDate.getHour() + ":" + String.format("%02d", Player.getPlayerDate().timeDate.getMinute()));
    playerView.setText("云团:" + Player.getPlayerDate().getMoney() + "   声望:" + Player.getPlayerDate().getPrestige());
    }
}


