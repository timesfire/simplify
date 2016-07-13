package com.simplewelcompage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.simplify.adapter.SimpleArrayAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        ArrayList<Object> list = new ArrayList<>();
        list.add(new User("张小二", "20"));
        list.add(new User("张小三", "21"));
        list.add(new User("张小四", "22"));
        list.add(new User("张小五", "23"));
        list.add(new User("张小六", "24"));
        list.add(new User("张小七", "25"));

        SimpleArrayAdapter adapter = new SimpleArrayAdapter.Builder(this)
                .itemLayoutId(R.layout.list_item)
                .from("name", "age")
                .to(R.id.name, R.id.age, R.id.button)
                .setListData(list)
                .setmChildViewClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button:
                                User user = (User) v.getTag();
                                Toast.makeText(ListActivity.this, "Button click " + user.name, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .setmViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        return false;
                    }
                })
                .build();


        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "item click" + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class User {
        String name;
        String age;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }

        public User(String name, String age) {
            this.name = name;
            this.age = age;
        }
    }

}
