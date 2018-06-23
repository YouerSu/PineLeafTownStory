package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.buildings.Building;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.OwnName;
import com.example.administrator.utils.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Game extends AppCompatActivity implements GameUI{
    private static ViewPager pager;
    private static List<View> pagerList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();
    static TextView timeView;
    static TextView playerView;
    private static long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GameTime.info = Db.setInfo(this);
        getBuildingDate();
        getDefaultBuilding();
        setText();
        getPlayerDate();
        //时间流逝
        timeView = findViewById(R.id.clock);
        playerView = findViewById(R.id.player);
        Timer timer = new Timer();
        timer.schedule(GameTime.timeDate, 3000L, 2000L);
    }

    public static void setBuiling(){
        for (Building building:GameTime.buildings){
            setBuilding(building.getName(),R.layout.building);
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

    }

    @Override
    protected void onStop() {
        saveAllDate();
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                saveAllDate();
                Toast.makeText(this, "你...确定要离去吗?", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getDefaultBuilding() {
        setBuilding("建筑小屋",R.layout.workshop);
        setBuilding("银行",R.layout.bank);
        setBuilding("工厂",R.layout.factory);
        setBuilding("洞窟",R.layout.cave);
    }

    private static void setBuilding(String s,int a){
        titleList.add(s);
        View view = View.inflate(this, a, null);
        pagerList.add(view);
    }

    public void getPlayerDate() {
//获取玩家信息


        if (!Player.name.equals("啦啦啦,我是没有名字的傻瓜")) return;
            getDialog("嘿,啥子信心` ", "aa");
            getDialog("测试测试", "bb");
            showTextDialog("我为你准备了一家商店,你准备叫他什么?", 2);
            showTextDialog("你还记得,你的名字吗?",1);
            iDate.close();
            data.close();
            setPlayerText();

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

    public static void setTimeView(){

        setPlayerText();
    }

    public static void setPlayerText() {
        playerView.setText("");
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
//        Window window = alertDialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.BOTTOM);
//        TextView next;
//        if (Character.equals(Player.name)) {//玩家对话框
//            next = window.findViewById(R.id.pmessage);
//            ImageView npc = window.findViewById(R.id.player);
//            npc.setImageResource(R.drawable.ic_launcher_background);
//        } else {//NPC对话框
//            next = window.findViewById(R.id.message);
//            ImageView npc = window.findViewById(R.id.NPC);
//            switch (Character) {
//                case "banker":
//                    npc.setImageResource(R.mipmap.banker);
//                    break;
//                case "dd":
//                    npc.setImageResource(R.drawable.ic_launcher_background);
//                    break;
//                case "ss":
//                    npc.setImageResource(R.drawable.ic_launcher_background);
//                    break;
//                default:
//                    npc.setImageResource(R.drawable.ic_launcher_background);
//            }
//        }
//
//        next.setText(message);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
        return alertDialog;
    }

    public void build(View view) {
        //建筑小屋的监听
        int i = view.getId();
        switch (i) {
            case R.id.bshop:
                createBuilding(50000,100,20,2,0,4,0);
                break;
            case R.id.brestaurant:
                createBuilding(50000,100,20,2,0,4,0);
                break;
            case R.id.bhotel:
                createBuilding(50000,100,20,2,0,4,0);
                break;
        }

    }

    private void createBuilding(int i, int i1, int i2, int i3, int i4, int i5, int i6) {

        if (Player.money >= i) {
            SQLiteDatabase db = GameTime.info.getWritableDatabase();
            Player.money -= i;
            db.execSQL("create table if not exists "+Info.STAFF_AND_SHOP + GameTime.totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.NAME +" text,"+Info.CLEVER +" integer,"+Info.STRONG_LEVEL +" integer,"+Info.LOYALTY +" integer,"+Info.salary+" integer not null,"+Info.capacity+" integer,"+Info.customer+" integer)");
            db.execSQL("insert into " + Info.STAFF_AND_SHOP + GameTime.totalOfBuilding + "(" + Info.NAME + "," + Info.CLEVER + "," + Info.STRONG_LEVEL + "," + Info.LOYALTY + "," + Info.salary + "," + Info.capacity + "," + Info.customer+") values('建筑',"+i1+","+i2+","+i3+","+i4+","+i5+","+i6+")");
            db.execSQL("create table if not exists "+Info.wareHouse+ GameTime.totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.NAME +" text,"+Info.VOLUME +","+Info.sellPrice+" integer,"+Info.total+" integer)");
            GameTime.totalOfBuilding++;
            db.close();
            getDialog("等你下次回来我就帮你修好", "aaa");
        } else
            Toast.makeText(this, "你没有足够的金钱", Toast.LENGTH_SHORT).show();

    }

    public void bank(View view) {
        //银行的监听
        int i = view.getId();
        switch (i) {
            case R.id.loan:
                Log.i("a", "build: shop");
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
                if (Player.money>= GameTime.buildings.get(pager.getCurrentItem()).get(0).getCapacity()*2){
                    GameTime.buildings.get(pager.getCurrentItem()).get(0).setCapacity(GameTime.buildings.get(pager.getCurrentItem()).get(0).getCapacity()/2);
                    SQLiteDatabase db = GameTime.info.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Info.capacity, GameTime.buildings.get(pager.getCurrentItem()).get(0).getCapacity());
                    db.update(Info.STAFF_AND_SHOP + pager.getCurrentItem(), values, Info.id + "=?", new String[]{String.valueOf(0)});
                    db.close();}
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
    timeView.setText("第" + GameTime.timeDate.getYear() + "年     " + GameTime.season + "第" + GameTime.timeDate.getDay() + "天    " + Player.timeDate.getHour() + ":" + String.format("%02d", Player.timeDate.getMinute()));
    playerView.setText("云团:" + GameTime.playerDate.getMoney() + "      声望:" + GameTime.playerDate.getPrestige());
    }
}


