package com.example.administrator.storeboss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.buildings.Building;
import com.example.administrator.buildings.Item;
import com.example.administrator.utils.GameTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowStock{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        Bundle bundle = this.getIntent().getExtras();
        who = bundle.getInt("who");
        now = bundle.getInt("now");

        //不知道该怎么写,只好写了两边QWQ
        if (who==4){
            for (int i = 0; i<GameTime.buildings.get(now).size(); i++){
            Map<String,Object> item = new HashMap<>();
            Building thisBuilding = GameTime.buildings.get(now).get(i);
            String name = thisBuilding.getstrangeName();
            String strong = "安保:"+thisBuilding.getStrongLevel();
            String price = "工资:"+thisBuilding.getSalary();
            String gravitational = "引力:"+thisBuilding.getClever();
            item.put("name",name);
            item.put("volume",strong);
            item.put("sellPrice",price);
            item.put("total",gravitational);
            listItem.add(item);
        }
        }
        else{
        for (int i = 0; i< GameTime.allWare.get(now).size(); i++){
            Map<String,Object> item = new HashMap<>();
            Item thisWare = GameTime.allWare.get(now).get(i);
            String name = thisWare.getname();
            String volume = "体积:"+thisWare.getVolume();
            String price = "价格"+thisWare.getSellPrice();
            String total = "总量:"+thisWare.getTotal();
            item.put("name",name);
            item.put("volume",volume);
            item.put("sellPrice",price);
            item.put("total",total);
            listItem.add(item);
        //int volume, int oPrice,int popular,int whenPopular
        }}

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    if (who==4) return;
                    final AlertDialog alertDialog = new AlertDialog.Builder(ShowStock.this).create();
                    alertDialog.show();
                    alertDialog.setContentView(R.layout.textin);
                    Window window = alertDialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    final EditText editText = window.findViewById(R.id.tname);
                    SpannableString ss = new SpannableString("重新输入价格");
                    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);
                    ss.setSpan(ass,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editText.setHint(ss);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    window.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            GameTime.allWare.get(now).get(i).setSellPrice(Integer.valueOf(editText.getText().toString()));
                            alertDialog.dismiss();
                            Game.saveAllDate();
            }
        });

    }});
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                String s ;
                if (now==4)
                    s="辞去员工:";
                else
                    s="销毁物品:";
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowStock.this).setTitle(s+ GameTime.allWare.get(now).get(i).getname()).setMessage("确定将它从你的仓库移除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int b) {
                        if (who==4)
                            GameTime.buildings.get(now).remove(i);
                        else
                            GameTime.allWare.get(now).remove(i);
                    }
                })
                .create()
                .show();
                return true;
            }
        });
        TextView textView = findViewById(R.id.showVolume);
        textView.setText("容量: "+ Item.getAllVolume(GameTime.allWare.get(now))+"/"+GameTime.buildings.get(now).get(0).getCapacity());
    }

    private void setText(String s,int id) {
        TextView textView = findViewById(id);
        textView.setText(s);
    }


}
