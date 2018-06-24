package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Item;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.ShowAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class Game extends AppCompatActivity implements GameUI{
    public static GameTime timeDate;
    private static ViewPager pager;
    private static List<View> pagerList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();
    static TextView timeView;
    static TextView playerView;
    private static long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        timeDate = new GameTime(this);
        timeDate.getTimeDate(Info.PLACE_NAME);
        setContentView(R.layout.activity_game);
        GameTime.info = Db.setInfo(this);
        getDefaultBuilding();
        setText();
        //时间流逝
        timeView = findViewById(R.id.clock);
        playerView = findViewById(R.id.player);
        Timer timer = new Timer();
        timer.schedule(timeDate, 3000L, 2000L);
    }

    @Override
    public void showStockDialogue(final List<ShowAdapter> items){
        List<Map<String,String>> listItem = new ArrayList<>();
        for (ShowAdapter item:items)
            listItem.add(item.UIPageAdapter());
        AlertDialog alertDialog = getDialog(R.layout.activity_show_stock);
        SimpleAdapter sa = new SimpleAdapter(this,listItem,R.layout.item_list,new String[]{"name","volume","sellPrice","total"},new int[]{R.id.name,R.id.lt1,R.id.lt2,R.id.lt3});
        ListView list = findViewById(R.id.stockList);
        list.setAdapter(sa);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                items.get(i).onClickListener();
            }
        });

    }

    @Override
    public boolean trueOrFalseDialogue(String message) {
        AlertDialog alertDialog = getDialog(R.layout.crystal);
        alertDialog.setTitle(message);
        return false;
    }

    public void setBuiling(){
        for (Building building:timeDate.buildings){
        showBuilding(building.getName(),R.layout.building);
        }
    }

    public void dayHarvest() {
        //昼夜交替,显示一天的收获

    }

    @Override
    public void reName(final OwnName item) {
        AlertDialog alertDialog = getInputDialog("请输入新名称");
        Window window = alertDialog.getWindow();
        final EditText editText = window.findViewById(R.id.tname);
        window.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setName(editText.getText().toString());
            }
        });
    }

    @Override
    public void dialogueBox(String message) {
        final AlertDialog alertDialog = getDialog(R.layout.crystal);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        TextView next;
        if (message.substring(0,message.indexOf(":")).equals(GameTime.playerDate.getName())) {
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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    protected void onDestroy() {
        saveDate();
        super.onDestroy();
    }

    private static void saveDate(){
        timeDate.saveDate();
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

        if (GameTime.playerDate.getMoney()>= Info.BUILDING_PRICE) {
            GameTime.playerDate.setMoney(GameTime.playerDate.getMoney()-Info.BUILDING_PRICE);
            new Building("建筑",1,1,1,1).createNewBuilding();
            dialogueBox("ada:等你下次回来我就帮你修好");
        } else
            Toast.makeText(this, "你没有足够的金钱", Toast.LENGTH_SHORT).show();

    }

    public void bank(View view) {
        //银行的监听
        int i = view.getId();
        switch (i) {
            case R.id.loan:
                Log.i("a", "build:");
                break;
            case R.id.Inquire:
                Log.i("a", "build: restaurant");
                break;
            case R.id.sell:
                Log.i("a", "build: hotel");
                break;
        }

    }

    private void building(int id, int who) {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putInt("who",who);
        bundle.putInt("now",pager.getCurrentItem());
        switch (id) {
            case R.id.special:
                bundle.putInt("who",4);
            case R.id.buy:
                intent.setClass(this,ShowBuy.class);
                break;
            case R.id.activity:
                intent.setClass(this,ShowActivity.class);
                break;
            case R.id.expansion:
                if (GameTime.playerDate.getMoney()>= timeDate.buildings.get(pager.getCurrentItem()).getCapacity()*2){
                    timeDate.buildings.get(pager.getCurrentItem()).setCapacity(timeDate.buildings.get(pager.getCurrentItem()).getCapacity()+timeDate.buildings.get(pager.getCurrentItem()).getCapacity()/3);
                    }
                else Toast.makeText(this,"你没有足够的金钱",Toast.LENGTH_SHORT).show();
                return;
            case R.id.InfoOfShop:
                bundle.putInt("who",4);
            case R.id.stock:
                intent.setClass(this,ShowStock.class);
                break;
        }
            intent.putExtras(bundle);
            startActivity(intent);
    }

    public void hotel(View view) {
        //旅馆监听
        int i = view.getId();
        building(i, 2);
    }

    public void restaurant(View view) {
        //餐馆监听
        int i = view.getId();
        building(i,3);
    }

    public void shop(View view) {
            //超市监听
            int i = view.getId();
            building(i, 1);

    }


    @Override
    public void refreshUI() {
        String season = null;
        switch (timeDate.getMonth()) {
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
    timeView.setText("第" + timeDate.getYear() + "年     " + season + "第" + timeDate.getDay() + "天    " + timeDate.getHour() + ":" + String.format("%02d", timeDate.getMinute()));
    playerView.setText("云团:" + GameTime.playerDate.getMoney() + "      声望:" + GameTime.playerDate.getPrestige());
    }
}


