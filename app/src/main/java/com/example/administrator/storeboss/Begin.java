package com.example.administrator.storeboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Begin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
    }

    public void begin(View view){
        Intent it = new Intent();
        it.setClass(this, Game.class);
        startActivity(it);
        finish();
    }

    public void exit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void help(View view) {
        Intent it = new Intent();
        it.setClass(this, Help.class);
        startActivity(it);
    }
}



