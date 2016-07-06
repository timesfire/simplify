package com.simplewelcompage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simplify.WelcomPage;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        new WelcomPage(this).show(R.drawable.start1, R.drawable.start2, R.drawable.start3);
    }
}
