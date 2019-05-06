package com.kaoyan.kaoyandemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.PostMessageActivity;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {


    public CommunityFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCommunityList();
    }

    private void getCommunityList() {
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
        CommunityAdapter communityAdapter = new CommunityAdapter(getContext(), list, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(communityAdapter);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivity(new Intent(getContext(), PostMessageActivity.class));
    }
}
