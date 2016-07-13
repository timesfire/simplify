package com.simplewelcompage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.simplify.WelcomPage;

public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

//        new WelcomPage(this).show(R.drawable.start1, R.drawable.start2, R.drawable.start3);

        new WelcomPage(this).show(new WelcomPage.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(WelcomActivity.this, "dismiss", Toast.LENGTH_SHORT).show();
            }
        }, R.drawable.start1, R.drawable.start2, R.drawable.start3);


    }
}
