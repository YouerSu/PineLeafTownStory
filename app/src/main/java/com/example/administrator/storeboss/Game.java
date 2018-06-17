package com.example.administrator.storeboss;

import org.xmlpull.v1.XmlPullParserException;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Item;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class Game extends AppCompatActivity {
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
        timer.schedule(Player.timeDate, 3000L, 2000L);
    }



    private void dayByDay() {
        //昼夜交替,显示一天的收获
    }

    public static void saveAllDate() {
        //将所有数据存入数据库
        SQLiteDatabase db = GameTime.info.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = Player.savePlayerDate(db);
        for (int count = 0; count < GameTime.totalOfBuilding; count++) {
           Building.clearSql(Info.BUILDING);
           Building.clearSql(Info.wareHouse+count);
            for (int m = 0; m < GameTime.cAndE.get(count).size(); m++) {
                values.clear();
                values.put(Info.CLEVER, GameTime.cAndE.get(count).get(m).getClever());
                values.put(Info.STRONG_LEVEL, GameTime.cAndE.get(count).get(m).getStrongLevel());
                values.put(Info.capacity,GameTime.cAndE.get(count).get(m).getCapacity());
                values.put(Info.LOYALTY, GameTime.cAndE.get(count).get(m).getLoyalty());
                values.put(Info.salary, GameTime.cAndE.get(count).get(m).getSalary());
                values.put(Info.customer, GameTime.cAndE.get(count).get(m).getCustomer());
                db.insert(Info.STAFF_AND_SHOP + count,null ,values);
            }
                for (int m = 0; m < GameTime.allWare.get(count).size(); m++) {
                values.clear();
                values.put(Info.NAME, GameTime.allWare.get(count).get(m).getname());
                values.put(Info.sellPrice, GameTime.allWare.get(count).get(m).getSellPrice());
                values.put(Info.total, GameTime.allWare.get(count).get(m).getTotal());
                db.insert(Info.wareHouse + count,null ,values);
            }}
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

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

    private void setBuilding(String s,int a){
        titleList.add(s);
        View view = View.inflate(this, a, null);
        pagerList.add(view);
    }

    public void getPlayerDate() {
//获取玩家信息
        SQLiteDatabase data = GameTime.info.getWritableDatabase();
        Cursor iDate;
        iDate = data.query(Info.PLAYER, null, Info.id + "=?", new String[]{"1"}, null, null, null);
        if (iDate == null) return;
            Player.name = iDate.getString(iDate.getColumnIndex(Info.NAME));
            Player.money = iDate.getInt(iDate.getColumnIndex(Info.MONEY));
            Player.prestige = iDate.getInt(iDate.getColumnIndex(Info.PRESTIGE));
            pager.setCurrentItem(iDate.getInt(iDate.getColumnIndex(Info.coordinate)));
            Player.timeDate = new GameTime(iDate.getInt(iDate.getColumnIndex(Info.MINUTE)), iDate.getInt(iDate.getColumnIndex(Info.HOUR)), iDate.getInt(iDate.getColumnIndex(Info.DAY)), iDate.getInt(iDate.getColumnIndex(Info.MONTH)), iDate.getInt(iDate.getColumnIndex(Info.YEAR)));
        if (!Player.name.equals("啦啦啦,我是没有名字的傻瓜")) return;
            showDialog("嘿,啥子信心` ", "aa");
            showDialog("测试测试", "bb");
            showTextDialog("我为你准备了一家商店,你准备叫他什么?", 2);
            showTextDialog("你还记得,你的名字吗?",1);
            iDate.close();
            data.close();
            setPlayerText();

    }

    private void showTextDialog(final String s, final int i) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.textin);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        final EditText editText = window.findViewById(R.id.tname);
        SpannableString ss = new SpannableString(s);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);
        ss.setSpan(ass,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(ss);
        window.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                SQLiteDatabase db = GameTime.info.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Info.NAME,string);
                if (i==1){
                db.update(Info.PLAYER, values, Info.id + "=?", new String[]{"1"});
                Player.name = string;
                }else if (i==2){
                    int a = GameTime.totalOfBuilding - 1;
                db.update(Info.BUILDING + a,values,Info.NAME +"=?",new String[]{"建筑"});
                }

                db.close();
                alertDialog.dismiss();
            }
        });

    }

    public static void setTimeView(){
        timeView.setText("第" + Player.timeDate.getYear() + "年     " + GameTime.season + "第" + Player.timeDate.getDay() + "天    " + Player.timeDate.getHour() + ":" + String.format("%02d", Player.timeDate.getMinute()));
        setPlayerText();
    }

    public static void setPlayerText() {
        playerView.setText("云团:" + Player.money + "      声望:" + Player.prestige);
    }

    public void getBuildingDate(){
//        获取建筑信息
        HashMap<String,Item> item = new HashMap<>();
        XmlResourceParser xrp = getResources().getXml(R.xml.items);
        try {
            while (xrp.getEventType()!=XmlResourceParser.END_DOCUMENT){
            if (xrp.getEventType() == XmlResourceParser.START_TAG){
                String tagName = xrp.getName();
                if (tagName.equals("item")){
                    int volume = Integer.parseInt(xrp.getAttributeValue(2));
                    int price = Integer.parseInt(xrp.getAttributeValue(1));
                    int popular = Integer.parseInt(xrp.getAttributeValue(0));
                    int whenPopular = Integer.parseInt(xrp.getAttributeValue(3));
                    item.put(xrp.nextText(),new Item(volume,price,popular,whenPopular));
                    //int volume, int oPrice,int popular,int whenPopular
                }
            }
            xrp.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        SQLiteDatabase data = GameTime.info.getWritableDatabase();
        Cursor iDate = data.query(Info.STAFF_AND_SHOP + GameTime.totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
        try {
            //用异常来跳出循环
            while (true) {
                iDate = data.query(Info.STAFF_AND_SHOP + GameTime.totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
                List<Building> individual = new ArrayList<>();
                while (iDate.moveToNext()) {
                    String name = iDate.getString(iDate.getColumnIndex(Info.NAME));
                    int strongLevel = iDate.getInt(iDate.getColumnIndex(Info.STRONG_LEVEL));
                    int clever = iDate.getInt(iDate.getColumnIndex(Info.CLEVER));
                    int salary = iDate.getInt(iDate.getColumnIndex(Info.salary));
                    int loyalty = iDate.getInt(iDate.getColumnIndex(Info.LOYALTY));
                    int capacity = iDate.getInt(iDate.getColumnIndex(Info.capacity));
                    int customer = iDate.getInt(iDate.getColumnIndex(Info.customer));
                    individual.add(new Building(loyalty, name, strongLevel, clever, salary, capacity,customer));
                    if (capacity>0){
                    titleList.add(name);
                    if (capacity > 500) {
                        View view = View.inflate(this, R.layout.shop, null);
                        pagerList.add(view);
                    }

                    if (capacity < 500 && salary > 0) {
                        View view = View.inflate(this, R.layout.hotel, null);
                        pagerList.add(view);
                    }

                    if (capacity < 500 && salary == 0) {
                        View view = View.inflate(this, R.layout.restaurant, null);
                        pagerList.add(view);
                    }}

                }
                GameTime.cAndE.add(individual);
                iDate = data.query(Info.wareHouse + GameTime.totalOfBuilding, null, null, null, null, null, null);
                List<Item> items = new ArrayList<>();
                while (iDate.moveToNext()) {
                    String name = iDate.getString(iDate.getColumnIndex(Info.NAME));
                    int sellPrice = iDate.getInt(iDate.getColumnIndex(Info.sellPrice));
                    int total = iDate.getInt(iDate.getColumnIndex(Info.total));
                    items.add(new Item(name, item.get(name).getVolume(),item.get(name).getOriginalPrice(), sellPrice, item.get(name).getPopular(),total,item.get(name).getWhenPopular()));
                }
                GameTime.allWare.add(items);
                GameTime.totalOfBuilding++;

            }
        } catch (Exception a) {
        }
        data.close();
        iDate.close();

    }

    public void setText() {
        //加载适配器
        pager = findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(pagerList, titleList);
        pager.setAdapter(adapter);
    }

    private void showDialog(String message, String Character) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.crystal);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        TextView next;
        if (Character.equals(Player.name)) {//玩家对话框
            next = window.findViewById(R.id.pmessage);
            ImageView npc = window.findViewById(R.id.player);
            npc.setImageResource(R.drawable.ic_launcher_background);
        } else {//NPC对话框
            next = window.findViewById(R.id.message);
            ImageView npc = window.findViewById(R.id.NPC);
            switch (Character) {
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
            showDialog("等你下次回来我就帮你修好", "aaa");
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
                if (Player.money>= GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity()*2){
                    GameTime.cAndE.get(pager.getCurrentItem()).get(0).setCapacity(GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity()/2);
                    SQLiteDatabase db = GameTime.info.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Info.capacity, GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity());
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



}


