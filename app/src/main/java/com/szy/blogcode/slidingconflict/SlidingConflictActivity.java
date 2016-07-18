package com.szy.blogcode.slidingconflict;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.szy.blogcode.R;
import com.szy.blogcode.view.HorizontalScrollView;

import java.util.ArrayList;

public class SlidingConflictActivity extends AppCompatActivity {

    private HorizontalScrollView listview_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_conflict);
        listview_container = (HorizontalScrollView)findViewById(R.id.listview_container);
        for (int i=0;i<3;i++) {
            ListView listView=createListView();
            listview_container.addView(listView);
        }
        listview_container.requestLayout();
    }

    private ListView createListView() {
        ListView listview = new ListView(this);
        listview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ArrayList<String> list = new ArrayList<>();
        for (int i=0;i<20;i++) {
            list.add("text"+i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,
                R.layout.item_sliding_conflict,
                list
        );
        listview.setAdapter(adapter);
        return listview;
    }
}
