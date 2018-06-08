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
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.Sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    public static Sql info;
    private static ViewPager pager;
    private static List<View> pagerList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();
    private static GameTime timeDate;
    static TextView timeView;
    static TextView playerView;
    static String season = "春季日";
    private static long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        info = Db.setInfo(this);
        getBuildingDate();
        getDefaultBuilding();
        setText();
        getPlayerDate();
        //时间流逝
        timeView = findViewById(R.id.clock);
        Timer timer = new Timer();
        final TimerTask time = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setPlayerText();
                        if (timeDate.getMinute() >= 60) {
                            timeDate.setMinute(0);
                            if (timeDate.getHour() >= 24) {
                                timeDate.setHour(7);
                                GameTime.theft();
                                saveAllDate();
                                dayByDay();
                                GameTime.setCustomer();
                                if (timeDate.getDay() > 25) {
                                    timeDate.setDay(1);
                                    GameTime.investment();
                                    if (timeDate.getMonth() > 4) {
                                        switch (timeDate.getmonth()) {
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
                                        timeDate.setMonth(1);
                                        timeDate.setYear();
                                        GameTime.politicalChanges();
                                    }
                                }
                            }
                        }
                        timeView.setText("第" + timeDate.getyear() + "年     " + season + "第" + timeDate.getday() + "天    " + timeDate.gethour() + ":" + String.format("%02d", timeDate.getminute()));
                    }
                });
            }
        };
        timer.schedule(time, 50L, 10000L);
        timer.schedule(timeDate, 3000L, 3000L);
    }



    private void dayByDay() {
        //昼夜交替,显示一天的收获
    }

    public static void deleteSql(String tableName){
        info.getWritableDatabase().execSQL("DELETE FROM " + tableName);
    }

    public static void saveAllDate() {
        //将所有数据存入数据库
        SQLiteDatabase db = info.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(Info.Tm, GameTime.money);
        values.put(Info.Tprestige, GameTime.prestige);
        values.put(Info.lastyear, timeDate.getyear());
        values.put(Info.lastday, timeDate.getday());
        values.put(Info.lastmonth, timeDate.getmonth());
        values.put(Info.lastminute, timeDate.getminute());
        values.put(Info.nowPager, pager.getCurrentItem());
        db.update(Info.Tp, values, Info.id + "=?", new String[]{"1"});
        for (int count = 0; count < GameTime.totalOfBuilding; count++) {
           deleteSql(Info.Ts+count);
           deleteSql(Info.wareHouse+count);
            for (int m = 0; m < GameTime.cAndE.get(count).size(); m++) {
                values.clear();
                values.put(Info.Tclever, GameTime.cAndE.get(count).get(m).getClever());
                values.put(Info.sl, GameTime.cAndE.get(count).get(m).getStrongLevel());
                values.put(Info.capacity,GameTime.cAndE.get(count).get(m).getCapacity());
                values.put(Info.loy, GameTime.cAndE.get(count).get(m).getLoyalty());
                values.put(Info.salary, GameTime.cAndE.get(count).get(m).getSalary());
                values.put(Info.customer, GameTime.cAndE.get(count).get(m).getCustomer());
                db.insert(Info.Ts + count,null ,values);
            }
                for (int m = 0; m < GameTime.allWare.get(count).size(); m++) {
                values.clear();
                values.put(Info.Tn, GameTime.allWare.get(count).get(m).getname());
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
        setBuilding("洞窟",R.layout.crystal);
    }

    private void setBuilding(String s,int a){
        titleList.add(s);
        View view = View.inflate(this, a, null);
        pagerList.add(view);
    }

    public void getPlayerDate() {
//获取玩家信息
        SQLiteDatabase data = info.getWritableDatabase();
        Cursor iDate;
        iDate = data.query(Info.Tp, null, Info.id + "=?", new String[]{"1"}, null, null, null);
        if (iDate != null) {
            while (iDate.moveToNext()) {
                GameTime.name = iDate.getString(iDate.getColumnIndex(Info.Tn));
                GameTime.money = iDate.getInt(iDate.getColumnIndex(Info.Tm));
                GameTime.prestige = iDate.getInt(iDate.getColumnIndex(Info.Tprestige));
                pager.setCurrentItem(iDate.getInt(iDate.getColumnIndex(Info.nowPager)));
                timeDate = new GameTime(iDate.getInt(iDate.getColumnIndex(Info.lastminute)), iDate.getInt(iDate.getColumnIndex(Info.lasthour)), iDate.getInt(iDate.getColumnIndex(Info.lastday)), iDate.getInt(iDate.getColumnIndex(Info.lastmonth)), iDate.getInt(iDate.getColumnIndex(Info.lastyear)));
            }
            if (GameTime.name.equals("啦啦啦,我是没有名字的傻瓜")) {
                showDialog("嘿,啥子信心` ", "aa");
                showDialog("测试测试", "bb");
                showTextDialog("我为你准备了一家商店,你准备叫他什么?", 2);
                showTextDialog("你还记得,你的名字吗?",1);
            }
            iDate.close();
            data.close();
            setPlayerText();
        }
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
                SQLiteDatabase db = info.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Info.Tn,string);
                if (i==1){
                db.update(Info.Tp, values, Info.id + "=?", new String[]{"1"});
                GameTime.name = string;
                }else if (i==2){
                    int a = GameTime.totalOfBuilding - 1;
                db.update(Info.Ts + a,values,Info.Tn+"=?",new String[]{"建筑"});
                }

                db.close();
                alertDialog.dismiss();
            }
        });

    }

    public void setPlayerText() {
        playerView = findViewById(R.id.player);
        playerView.setText("云团:" + GameTime.money + "      声望:" + GameTime.prestige);
    }



    public void getBuildingDate(){
//        获取建筑信息
        HashMap<String,WareHouse> item = new HashMap<>();
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
                    item.put(xrp.nextText(),new WareHouse(volume,price,popular,whenPopular));
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

        SQLiteDatabase data = info.getWritableDatabase();
        Cursor iDate = data.query(Info.Ts + GameTime.totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
        try {
            //用异常来跳出循环
            while (true) {
                iDate = data.query(Info.Ts + GameTime.totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
                List<Building> individual = new ArrayList<>();
                while (iDate.moveToNext()) {
                    String name = iDate.getString(iDate.getColumnIndex(Info.Tn));
                    int strongLevel = iDate.getInt(iDate.getColumnIndex(Info.sl));
                    int clever = iDate.getInt(iDate.getColumnIndex(Info.Tclever));
                    int salary = iDate.getInt(iDate.getColumnIndex(Info.salary));
                    int loyalty = iDate.getInt(iDate.getColumnIndex(Info.loy));
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
                List<WareHouse> wareHouses = new ArrayList<>();
                while (iDate.moveToNext()) {
                    String name = iDate.getString(iDate.getColumnIndex(Info.Tn));
                    int sellPrice = iDate.getInt(iDate.getColumnIndex(Info.sellPrice));
                    int total = iDate.getInt(iDate.getColumnIndex(Info.total));
                    wareHouses.add(new WareHouse(name, item.get(name).getVolume(),item.get(name).getoPrice(), sellPrice, item.get(name).getPopular(),total,item.get(name).getWhenPopular()));
                }
                GameTime.allWare.add(wareHouses);
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
        if (Character.equals(GameTime.name)) {//玩家对话框
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

        if (GameTime.money >= i) {
            SQLiteDatabase db = info.getWritableDatabase();
            GameTime.money -= i;
            db.execSQL("create table if not exists "+Info.Ts+ GameTime.totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.Tclever+" integer,"+Info.sl+" integer,"+Info.loy+" integer,"+Info.salary+" integer not null,"+Info.capacity+" integer,"+Info.customer+" integer)");
            db.execSQL("insert into " + Info.Ts + GameTime.totalOfBuilding + "(" + Info.Tn + "," + Info.Tclever + "," + Info.sl + "," + Info.loy + "," + Info.salary + "," + Info.capacity + "," + Info.customer+") values('建筑',"+i1+","+i2+","+i3+","+i4+","+i5+","+i6+")");
            db.execSQL("create table if not exists "+Info.wareHouse+ GameTime.totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.Tv+","+Info.sellPrice+" integer,"+Info.total+" integer)");
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
                if (GameTime.money>= GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity()*2){
                    GameTime.cAndE.get(pager.getCurrentItem()).get(0).setCapacity(GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity()/2);
                    SQLiteDatabase db = info.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Info.capacity, GameTime.cAndE.get(pager.getCurrentItem()).get(0).getCapacity());
                    db.update(Info.Ts + pager.getCurrentItem(), values, Info.id + "=?", new String[]{String.valueOf(0)});
                    db.close();}
                else Toast.makeText(this,"你没有足够的金钱",Toast.LENGTH_SHORT).show();
                return;
            case R.id.stock:
                intent.setClass(this,ShowStock.class);
                break;
            case R.id.InfoOfShop:
                intent.setClass(Game.this,ShowInfo.class);
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


