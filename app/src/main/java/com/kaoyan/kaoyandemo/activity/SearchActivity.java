package com.kaoyan.kaoyandemo.activity;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setToolBar();
        getSearchList();
    }

    private void getSearchList() {
        String json = "[\n" +
            "    {\n" +
            "        \"schoolName\":\"名称\",\n" +
            "        \"schoolDepartment\":\"院系\",\n" +
            "        \"schoolMajor\":\"专业\",\n" +
            "        \"schoolMajorDes\":\"描述\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"schoolName\":\"名称\",\n" +
            "        \"schoolDepartment\":\"院系\",\n" +
            "        \"schoolMajor\":\"专业\",\n" +
            "        \"schoolMajorDes\":\"描述\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"schoolName\":\"名称\",\n" +
            "        \"schoolDepartment\":\"院系\",\n" +
            "        \"schoolMajor\":\"专业\",\n" +
            "        \"schoolMajorDes\":\"描述\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"schoolName\":\"名称\",\n" +
            "        \"schoolDepartment\":\"院系\",\n" +
            "        \"schoolMajor\":\"专业\",\n" +
            "        \"schoolMajorDes\":\"描述\"\n" +
            "    }\n" +
            "]";
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) JSON.parseObject(json, List.class);
        SearchAdapter searchAdapter = new SearchAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("搜索");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
