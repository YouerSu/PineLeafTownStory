package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.Sql;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity{
    private static int totalOfBuilding = 0;
    public static int money;
    public static int prestige;
    private static String name;
    private ViewPager pager;
    private List<View> pagerList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    public static List<List<Building>> cAndE = new ArrayList<>();
    public static List<List<WareHouse>> allWare = new ArrayList<>();
    private static Sql info;
    private static GameTime timeDate;
    TextView timeView;
    TextView playerView;
    String season = "春季日";
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                        setPalyerText();
                        if (timeDate.getMinute() >= 60) {
                            timeDate.setMinute(0);
                            if (timeDate.getHour() >= 24) {
                                timeDate.setHour(7);
                                GameTime.theft();
                                saveAllDate();
                                dayAndDay();
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
        timer.schedule(timeDate, 7000L, 5000L);
    }

    private void dayAndDay() {
        //昼夜交替,显示一天的收获


    }

    private void saveAllDate() {
        //将所有数据存入数据库
        SQLiteDatabase db = info.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Info.Tm, money);
        values.put(Info.Tprestige,prestige);
        values.put(Info.lastyear, timeDate.getyear());
        values.put(Info.lastday, timeDate.getday());
        values.put(Info.lastmonth, timeDate.getmonth());
        values.put(Info.lastminute, timeDate.getminute());
        values.put(Info.nowPager, pager.getCurrentItem());
        db.update(Info.Tp, values, Info.id + "=?", new String[]{"1"});
        for (int cout = 0; cout < totalOfBuilding; cout++) {
            for (int m = 0; m < cAndE.get(cout).size(); m++) {
                values.clear();
                values.put(Info.Tclever, cAndE.get(cout).get(m).getClever());
                values.put(Info.sl, cAndE.get(cout).get(m).getStrongLevel());
                values.put(Info.loy, cAndE.get(cout).get(m).getLoyalty());
                values.put(Info.salary, cAndE.get(cout).get(m).getSalary());
                values.put(Info.capacity, cAndE.get(cout).get(m).getCapacity());
                values.put(Info.customer, cAndE.get(cout).get(m).getCustomer());
                db.update(Info.Ts + cout, values, Info.id + "=?", new String[]{String.valueOf(m)});
            }
                for (int m = 0; m < allWare.get(cout).size(); m++) {
                values.clear();
                values.put(Info.Tn, allWare.get(cout).get(m).getname());
                values.put(Info.Tv, allWare.get(cout).get(m).getVolume());
                values.put(Info.sellPrice, allWare.get(cout).get(m).getSellPrice());
                values.put(Info.total, allWare.get(cout).get(m).getTotal());
                db.update(Info.wareHouse + cout, values, Info.id + "=?", new String[]{String.valueOf(m)});
            }
            if (allWare.get(cout).size()<=0){
                db.delete(Info.wareHouse + cout, Info.id + "=?", new String[]{String.valueOf(cout)});}
        }
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
        titleList.add("建筑小屋");
        View view = View.inflate(this, R.layout.workshop, null);
        pagerList.add(view);
        titleList.add("银行");
        view = View.inflate(this, R.layout.bank, null);
        pagerList.add(view);
        titleList.add("工厂");
        view = View.inflate(this, R.layout.factory, null);
        pagerList.add(view);
    }

    public void getPlayerDate() {
//获取玩家信息
        SQLiteDatabase data = info.getWritableDatabase();
        Cursor iDate;
        iDate = data.query(Info.Tp, null, Info.id + "=?", new String[]{"1"}, null, null, null);
        if (iDate != null) {
            while (iDate.moveToNext()) {
                name = iDate.getString(iDate.getColumnIndex(Info.Tn));
                money = iDate.getInt(iDate.getColumnIndex(Info.Tm));
                prestige = iDate.getInt(iDate.getColumnIndex(Info.Tprestige));
                pager.setCurrentItem(iDate.getInt(iDate.getColumnIndex(Info.nowPager)));
                timeDate = new GameTime(iDate.getInt(iDate.getColumnIndex(Info.lastminute)), iDate.getInt(iDate.getColumnIndex(Info.lasthour)), iDate.getInt(iDate.getColumnIndex(Info.lastday)), iDate.getInt(iDate.getColumnIndex(Info.lastmonth)), iDate.getInt(iDate.getColumnIndex(Info.lastyear)));
            }
            if (name.equals("啦啦啦,我是没有名字的傻瓜")) {
                showDalog("嘿,啥子信心` ", "aa");
                showDalog("ddd", "bb");
                showTextDalog("你还记得,你的名字吗?",1);
                showTextDalog("我为你准备了一家商店,你准备叫他什么?", 2);
            }
            iDate.close();
            data.close();
            setPalyerText();
        }
    }

    private void showTextDalog(final String s, final int i) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.textin);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setTitle(s);
        final EditText text = window.findViewById(R.id.tname);
        ImageButton finish = window.findViewById(R.id.ok);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = text.getText().toString();
                SQLiteDatabase db = info.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Info.Tn,name);
                if (i==1){
                db.update(Info.Tp, values, Info.id + "=?", new String[]{"1"});
                }else if (i==2){
                    int a = totalOfBuilding - 1;
                db.update(Info.Ts + a,values,Info.Tn+"=?",new String[]{"建筑"});
                }
                db.close();
                alertDialog.dismiss();
            }
        });

    }

    public void setPalyerText() {
        playerView = findViewById(R.id.player);
        playerView.setText("云团:" + money + "      声望:" + prestige);
    }

    public void getBuildingDate() {
//        获取建筑信息
        SQLiteDatabase data = info.getWritableDatabase();
        Cursor iDate = data.query(Info.Ts + totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
        try {
            //用异常来跳出循环
            while (true) {
                iDate = data.query(Info.Ts + totalOfBuilding, null, Info.salary + ">?", new String[]{"-1"}, null, null, null);
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

                    if (capacity > 20 && capacity < 500 && salary > 0) {
                        View view = View.inflate(this, R.layout.hotel, null);
                        pagerList.add(view);
                    }

                    if (capacity > 20 && capacity < 500 && salary == 0) {
                        View view = View.inflate(this, R.layout.restaurant, null);
                        pagerList.add(view);
                    }}

                }
                cAndE.add(individual);
                iDate = data.query(Info.wareHouse + totalOfBuilding, null, null, null, null, null, null);
                List<WareHouse> wareHouses = new ArrayList<>();
                while (iDate.moveToNext()) {
                    String name = iDate.getString(iDate.getColumnIndex(Info.Tn));
                    int volume = iDate.getInt(iDate.getColumnIndex(Info.Tv));
                    int sellPrice = iDate.getInt(iDate.getColumnIndex(Info.sellPrice));
                    int total = iDate.getInt(iDate.getColumnIndex(Info.total));
                    wareHouses.add(new WareHouse(name, volume, 10, sellPrice, 10, total));
                }
                allWare.add(wareHouses);
                totalOfBuilding++;

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




    private void showDalog(String aaa, String Character) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.crystal);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        TextView next;
        if (Character.equals(name)) {//玩家对话框
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

        next.setText(aaa);
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

        if (money >= i) {
            SQLiteDatabase db = info.getWritableDatabase();
            money -= i;
            db.execSQL("create table if not exists "+Info.Ts+totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.Tclever+" integer,"+Info.sl+" integer,"+Info.loy+" integer,"+Info.salary+" integer not null,"+Info.capacity+" integer,"+Info.customer+" integer)");
            db.execSQL("insert into " + Info.Ts + totalOfBuilding + "(" + Info.Tn + "," + Info.Tclever + "," + Info.sl + "," + Info.loy + "," + Info.salary + "," + Info.capacity + "," + Info.customer+") values('建筑',"+i1+","+i2+","+i3+","+i4+","+i5+","+i6+")");
            db.execSQL("create table if not exists "+Info.wareHouse+totalOfBuilding+"("+Info.id+" integer primary key autoincrement,"+Info.Tn+" text,"+Info.Tv+","+Info.sellPrice+" integer,"+Info.total+" integer)");
            totalOfBuilding++;
            db.close();
            showDalog("好,周一帮你修好", "aaa");
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

    public void shop(View view) {
        //超市监听
        int i = view.getId();
        building(i, 1);

    }

    //这段代码,我真的很想删掉....看着十分不舒服

    private void building(int id, int who) {
        switch (id) {
            case R.id.buy:
                buy(who);
                break;
            case R.id.employee:
                employee(who);
                break;
            case R.id.activity:
                activity(who);
                break;
            case R.id.expansion:
                expansion(who);
                break;
            case R.id.stock:
                stock(who);
                break;
            case R.id.InfoOfShop:
                infoOfShop(who);
                break;
        }
    }
    //各个按钮的实现
    private void infoOfShop(int who) {
       //所有参数
    }

    private void stock(int who) {

    }

    private void expansion(int who) {
        switch (who){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;}
    }

    private void activity(int who) {
        switch (who){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;}
    }

    private void employee(int who) {
        switch (who){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;}
    }

    private void buy(int who) {
        switch (who){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
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
}


