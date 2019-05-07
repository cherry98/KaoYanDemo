package com.kaoyan.kaoyandemo.activity;


import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.SchoolAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SchoolActivity extends BaseActivity{
    @BindView(R.id.recyclerSchoolView)
    RecyclerView recyclerSchoolView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
        setToolBar();
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择学校");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        String json = "[\n" +
                "    {\n" +
                "        \"schoolid\":\"1\",\n" +
                "        \"schoolname\":\"北大\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"schoolid\":\"1\",\n" +
                "        \"schoolname\":\"北大\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"schoolid\":\"1\",\n" +
                "        \"schoolname\":\"北大\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"schoolid\":\"1\",\n" +
                "        \"schoolname\":\"北大\"\n" +
                "    }\n" +
                "]";
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) JSON.parseObject(json, List.class);
        SchoolAdapter schoolAdapter = new SchoolAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerSchoolView.setLayoutManager(layoutManager);
        recyclerSchoolView.setHasFixedSize(true);
        recyclerSchoolView.setAdapter(schoolAdapter);
    }


}
