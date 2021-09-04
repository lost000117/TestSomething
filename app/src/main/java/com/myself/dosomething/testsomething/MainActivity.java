package com.myself.dosomething.testsomething;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myself.dosomething.baselibs.baseutil;
import com.myself.dosomething.baselibs.view.TestTouchView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseutil.context=getApplicationContext();
        baseutil.dpsize=getResources().getDimension(R.dimen.size1);
        inittouch();
    }

    private void inittouch(){
        TestTouchView touchview=findViewById(R.id.touchview);
        findViewById(R.id.clear).setOnClickListener(v -> {
            touchview.clear();
        });
    }
}