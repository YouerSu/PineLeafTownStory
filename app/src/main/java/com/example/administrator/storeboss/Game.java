package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.administrator.buildings.GameUI;
import com.example.administrator.buildings.ShowAdapter;
import com.example.administrator.character.Character;
import com.example.administrator.character.Player;
import com.example.administrator.utils.Info;
import com.example.administrator.utils.MyPagerAdapter;
import com.example.administrator.utils.Response;
import com.example.administrator.utils.Sql;
import com.example.administrator.utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends AppCompatActivity implements GameUI{

    private ViewPager pager;
    private static List<View> pagerList = new ArrayList<>();
    private static List<String> titleList = new ArrayList<>();
    TextView timeView;
    TextView playerView;
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
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
            timeView.setText(String.format("%s第%d天  %d:%02d",season,Player.getPlayerDate().timeDate.getDay(),Player.getPlayerDate().timeDate.getHour(), Player.getPlayerDate().timeDate.getMinute()));
            playerView.setText(String.format("云团:%d   声望:%d",Player.getPlayerDate().getMoney(),Player.getPlayerDate().getPrestige()));
        }
    }
    MyHandler handler = new MyHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Sql.setInfo(new Sql(this));
        Character.getAllDate();
        Player.createPlayerDate(this);
        timeView = findViewById(R.id.clock);
        playerView = findViewById(R.id.playerDate);
        findViewById(R.id.showCharacter).setOnClickListener((view) -> showListDialogue(Character.getCharacters()));
        findViewById(R.id.showBag).setOnClickListener((view)-> showListDialogue(Tools.toList(Player.getPlayerDate().getBag().values())));
        findViewById(R.id.showItem).setOnClickListener((view)-> showListDialogue(Tools.toList(Building.getBuildings().get(pager.getCurrentItem()).getItems().values())));
        setBuilding();
        setText();
    }

    public <T extends ShowAdapter>ListView changeList(final List<T> items){
        //接收参数，转化参数，展示参数
        List<Map<String,String>> listItem = new ArrayList<>();
        for (ShowAdapter item:items)
            listItem.add(item.UIPageAdapter());
        return getListView(listItem);
    }

    public ListView getListView(List<Map<String, String>> listItem) {
        SimpleAdapter sa = new SimpleAdapter(this,listItem, R.layout.item_list,new String[]{Info.NAME,Info.LT1,Info.LT2,Info.LT3},new int[]{R.id.name,R.id.lt1,R.id.lt2,R.id.lt3});
        AlertDialog alertDialog = getDialog(R.layout.show_list);
        ListView list = alertDialog.getWindow().findViewById(R.id.list);
        list.setAdapter(sa);
        return list;
    }

    @Override
    public <T extends ShowAdapter>void showListDialogue(final List<T> items) {
        final GameUI UI = this;
        changeList(items).setOnItemClickListener((adapterView, view, i, l) -> items.get(i).onClick(UI));
    }


    @Override
    public <T> void chooseDialogue(String message,T[] messages,T[] choose) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(message)
        .setItems((String[])messages, (dialog, which) -> choose[0] = messages[which]);
        builder.create().show();
    }

    public void setBuilding(){
        for (Building building: Building.getBuildings())
        showBuilding(building.getName(),R.layout.building);
    }

    @Override
    public<T> void reText(String messages,T[] name) {
        //强行转换如同虚设XD
        AlertDialog alertDialog = getInputDialog(messages);
        alertDialog.findViewById(R.id.ok).setOnClickListener((view)->{
            try {
                name[0] = (T)((EditText)(alertDialog.findViewById(R.id.text))).getText().toString();
                alertDialog.dismiss();
            }catch (ClassCastException e){
                Toast.makeText(this,"您输入的字符不符合格式",Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void dialogueBox(String message) {
        final AlertDialog alertDialog = getDialog(R.layout.dialoguebox);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        //对话框
        TextView next = window.findViewById(R.id.message);
        ImageView ima = window.findViewById(R.id.ima);
        switch (message.substring(0,message.indexOf(":"))) {
            case "banker":
                ima.setImageResource(R.mipmap.banker);
                break;
            case "dd":
                ima.setImageResource(R.drawable.ic_launcher_background);
                break;
            case "ss":
                ima.setImageResource(R.drawable.ic_launcher_background);
                break;
            default:
                ima.setImageResource(R.mipmap.player);
        }


        next.setText(message);
        next.setOnClickListener((View view)-> alertDialog.dismiss());

    }



    private static void saveDate(){
        Character.saveAllDate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String[] choose = new String[1];
        chooseDialogue("你...确定要离去吗？",new String[]{"离开","留下"},choose);
        new Response<String>(choose){
            @Override
            public void doThings() {
                if (getResult().equals("离开")) {
                    saveDate();
                    finish();
                }
            }
        };
        return true;
    }


    public void showBuilding(String s, int a){
        titleList.add(s);
        View view = View.inflate(this, a, null);
        pagerList.add(view);
    }

    private AlertDialog getInputDialog(String text) {
        AlertDialog alertDialog = getDialog(R.layout.input_dialogue);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        final EditText editText = window.findViewById(R.id.text);
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
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Player.getPlayerDate().setX_coordinate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private AlertDialog getDialog(int layout) {
        //创建Dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
        handler.sendEmptyMessage(0);
    }
}


