package com.example.administrator.storeboss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        return super.onKeyDown(keyCode, event);
    }

}
