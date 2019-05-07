package com.kaoyan.kaoyandemo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.MajorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MajorActivity extends BaseActivity {

    @BindView(R.id.recyclerMajorView)
    RecyclerView recyclerMajorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        ButterKnife.bind(this);
        setToolBar();
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择专业");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        String json = "[\n" +
            "    {\n" +
            "        \"majorid\":\"1\",\n" +
            "        \"majorname\":\"北大\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"majorid\":\"1\",\n" +
            "        \"majorname\":\"北大\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"majorid\":\"1\",\n" +
            "        \"majorname\":\"北大\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"majorid\":\"1\",\n" +
            "        \"majorname\":\"北大\"\n" +
            "    }\n" +
            "]";
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) JSON.parseObject(json, List.class);
        MajorAdapter majorAdapter = new MajorAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMajorView.setLayoutManager(layoutManager);
        recyclerMajorView.setHasFixedSize(true);
        recyclerMajorView.setAdapter(majorAdapter);
        majorAdapter.setOnItemClickListener(new MajorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String majorid, String majorname) {
                Intent intent = new Intent();
                intent.putExtra("majorid", majorid);
                intent.putExtra("majorname", majorname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
