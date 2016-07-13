package com.simplewelcompage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.simplify.adapter.SimpleArrayAdapter;

import java.util.LinkedHashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("WelcomActiviy", WelcomActivity.class);
        map.put("ListActiviy", ListActivity.class);


        final SimpleArrayAdapter adapter = new SimpleArrayAdapter.Builder(this)
                .itemLayoutId(android.R.layout.simple_list_item_1)
                .from("key")
                .to(android.R.id.text1)
                .setMapData(map)
                .build();

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map item = (Map) adapter.getItem(position);
                startActivity(new Intent(MainActivity.this, (Class<?>) item.get("value")));
            }
        });


    }

}
