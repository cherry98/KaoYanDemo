package com.kaoyan.kaoyandemo.activity;


import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        setToolBar();
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的收藏");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        String json = "[\n" +
            "    {\n" +
            "        \"community_name\":\"我不知大\",\n" +
            "        \"community_content\":\"ahhahah哈哈哈哈哈哈哈哈哈哈哈哈哈哈\",\n" +
            "        \"community_time\":\"19-5-6 19:40\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"community_name\":\"我不知大\",\n" +
            "        \"community_content\":\"ahhahah哈哈哈哈哈哈哈哈哈哈哈哈哈哈\",\n" +
            "        \"community_time\":\"19-5-6 19:40\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"community_name\":\"我不知大\",\n" +
            "        \"community_content\":\"ahhahah哈哈哈哈哈哈哈哈哈哈哈哈哈哈\",\n" +
            "        \"community_time\":\"19-5-6 19:40\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"community_name\":\"我不知大\",\n" +
            "        \"community_content\":\"ahhahah哈哈哈哈哈哈哈哈哈哈哈哈哈哈\",\n" +
            "        \"community_time\":\"19-5-6 19:40\"\n" +
            "    }\n" +
            "]";
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) JSON.parseObject(json, List.class);
        CommunityAdapter communityAdapter = new CommunityAdapter(this, list, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(communityAdapter);
    }
}
