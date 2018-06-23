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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.buildings.Building;
import com.example.administrator.utils.Db;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.GameUI;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.OwnName;

import java.util.ArrayList;
import java.util.List;
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

    public void setBuiling(){
        for (Building building:GameTime.buildings){
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
        if (message.substring(0,message.indexOf(":")).equals(GameTime.playerDate.getName())) {//玩家对话框
            next = window.findViewById(R.id.pmessage);
            ImageView npc = window.findViewById(R.id.player);
            npc.setImageResource(R.drawable.ic_launcher_background);
        } else {//NPC对话框
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {

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
        return alertDialog;
    }



    private void createBuilding() {

        if (GameTime.playerDate.getMoney()>= 20000) {
            GameTime.playerDate.setMoney(GameTime.playerDate.getMoney()-20000);
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
                if (GameTime.playerDate.getMoney()>= GameTime.buildings.get(pager.getCurrentItem()).getCapacity()*2){
                    GameTime.buildings.get(pager.getCurrentItem()).setCapacity(GameTime.buildings.get(pager.getCurrentItem()).getCapacity()+GameTime.buildings.get(pager.getCurrentItem()).getCapacity()/3);
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
    timeView.setText("第" + timeDate.getYear() + "年     " + GameTime.season + "第" + timeDate.getDay() + "天    " + timeDate.getHour() + ":" + String.format("%02d", timeDate.getMinute()));
    playerView.setText("云团:" + GameTime.playerDate.getMoney() + "      声望:" + GameTime.playerDate.getPrestige());
    }
}


