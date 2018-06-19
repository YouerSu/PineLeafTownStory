package com.example.administrator.storeboss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.buildings.Item;
import com.example.administrator.utils.GameTime;
import com.example.administrator.utils.Player;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowBuy extends Activity {

    private static int howMany = 0;
    private static int now;
    private static int who;
    private static ArrayList<Map<String,Object>> listItem = null;

    //试着写写泛型,不过没有什么用就是了
    public static <E> ArrayList<E> getItems(XmlResourceParser xrp, ArrayList<E> listItem) throws XmlPullParserException, IOException {
        ArrayList<E> listItemCopy = listItem;
        while (xrp.getEventType()!=XmlResourceParser.END_DOCUMENT){
            if (xrp.getEventType() == XmlResourceParser.START_TAG){
                String tagName = xrp.getName();
                if (tagName.equals("item")&&Integer.parseInt(xrp.getAttributeValue(1))<=10000){
                    Map<String,Object> item = new HashMap<>();
                    int volume = Integer.parseInt(xrp.getAttributeValue(2));
                    int price = Integer.parseInt(xrp.getAttributeValue(1));
                    int popular = Integer.parseInt(xrp.getAttributeValue(0));
                    int whenPopular = Integer.parseInt(xrp.getAttributeValue(3));
                    item.put("name",xrp.nextText());
                    item.put("volume",volume);
                    item.put("oPrice",price);
                    item.put("popular",popular);
                    item.put("whenPopular",whenPopular);
                    listItemCopy.add((E)item);
                    //int volume, int oPrice,int popular,int whenPopular
                }
            }
            xrp.next();
        }
        return listItemCopy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buy);
        Bundle bundle = this.getIntent().getExtras();
        who = bundle.getInt("who");
        now = bundle.getInt("now");
        XmlResourceParser xrp;
        if (who==4) {
            xrp = getResources().getXml(R.xml.special);
        }else{
            xrp = getResources().getXml(R.xml.items);}
        try {
                listItem = new ArrayList<>();
                listItem = getItems(xrp,listItem);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        SimpleAdapter sa = new SimpleAdapter(this,listItem,R.layout.item_list,new String[]{"name","volume","oPrice"},new int[]{R.id.name,R.id.volume,R.id.cost});
        ListView list = findViewById(R.id.buyList);
        list.setAdapter(sa);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int which, long l) {
                Log.i("yep", "buyItem: witch"+which);
                final Map<String,Object> items = listItem.get(which);
                if (howMany*(int)items.get("oPrice")<= Player.money&&howMany!=0&&GameTime.buildings.get(now).get(0).getCapacity()>= Item.getAllVolume(GameTime.allWare.get(now))+howMany*(int)items.get("volume")){
                    final AlertDialog alertDialog = new AlertDialog.Builder(ShowBuy.this).setCancelable(false).create();
                    alertDialog.show();
                    alertDialog.setContentView(R.layout.textin);
                    Window window = alertDialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    final EditText editText = window.findViewById(R.id.tname);
                    SpannableString ss = new SpannableString("输入销售价格");
                    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);
                    ss.setSpan(ass,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editText.setHint(ss);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    window.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("yep", "onClick: "+howMany*(int)items.get("oPrice"));
                            Player.money-=howMany*(int)items.get("oPrice");
                            Log.i("yep", "onClick: "+ Player.money);
                            GameTime.allWare.get(now).add(new Item(String.valueOf(items.get("name")),(int)items.get("volume"),(int)items.get("oPrice"),Integer.valueOf(editText.getText().toString()),(int)items.get("popular"),howMany,(int)items.get("whenPopular")));
                            howMany=0;
                            alertDialog.dismiss();
                            setText();
                            Game.saveAllDate();
                        }
                    });
                }else {
                    StringBuffer s = new StringBuffer();
                    if (GameTime.buildings.get(now).get(0).getCapacity()<= Item.getAllVolume(GameTime.allWare.get(now))+howMany*(int)items.get("volume")) s.append("\n你已经没有足够的空间容下这么多物品了");
                    if (howMany*(int)items.get("oPrice")>= Player.money) s.append("\n你没有足够的金钱");
                    if (howMany<=0)s.append("\n你没有选择需要的数量");
                    showText(s);}
            }
        });
    }

    private void showText(StringBuffer s) {
            Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


    public void setMany(View view) {
        switch (view.getId()){
            case R.id.plus:
                howMany+=10;
                break;
            case R.id.less:
                if (howMany>=10)
                howMany-=10;
                break;
        }
        setText();
    }

    private void setText() {
        TextView text = findViewById(R.id.showMany);
        text.setText(String.valueOf(howMany));
    }

}