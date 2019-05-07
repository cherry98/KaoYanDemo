package com.kaoyan.kaoyandemo.fragment;


import android.content.Intent;
import android.os.Bundle;

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
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.SearchActivity;
import com.kaoyan.kaoyandemo.adapter.SearchAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSearchList();
    }

    private void getSearchList() {
//        Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
//            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//            .build();
//        Api api = retrofit.create(Api.class);
//        Map<String, String> params = new HashMap<>();
//        params.put("typeid", "0");
//        params.put("keyword", "");
//
//        String vars = new JSONObject(params).toString();
//        Call<Object> call = api.kaoyanList(vars);
//        call.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if (response.isSuccessful()) {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Toast.makeText(getContext(), "请稍后再试", Toast.LENGTH_SHORT).show();
//            }
//        });
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
        SearchAdapter searchAdapter = new SearchAdapter(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
    }

    @OnClick(R.id.search)
    public void searchClick() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }
}
