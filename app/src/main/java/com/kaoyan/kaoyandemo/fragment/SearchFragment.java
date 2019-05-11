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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.SearchActivity;
import com.kaoyan.kaoyandemo.adapter.SearchAdapter;
import com.kaoyan.kaoyandemo.info.SearchInfo;
import com.kaoyan.kaoyandemo.utils.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<SearchInfo> list = new ArrayList<>();
    private SearchAdapter searchAdapter;

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
        searchAdapter = new SearchAdapter(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
        getSearchList();
    }

    private void getSearchList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("typeid", "0");
        params.put("keyword", "");

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.kaoyanList(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    JSONObject jsonObject = new JSONObject(new String(response.body().bytes()));
                    if (jsonObject.getInt("status") == 1) {
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SearchInfo searchInfo = new SearchInfo();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            searchInfo.setMajorId(jsonObject1.getString("majorId"));
                            searchInfo.setCreate_time(jsonObject1.getString("create_time"));
                            searchInfo.setYear(jsonObject1.getString("year"));
                            searchInfo.setTitle(jsonObject1.getString("title"));
                            searchInfo.setMajorDesc(jsonObject1.getString("majorDesc"));
                            searchInfo.setContent(jsonObject1.getString("content"));
                            searchInfo.setCalssName(jsonObject1.getString("calssName"));
                            searchInfo.setSchoolName(jsonObject1.getString("schoolName"));
                            searchInfo.setMajorName(jsonObject1.getString("majorName"));
                            list.add(searchInfo);
                        }
                        searchAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.search)
    public void searchClick() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }
}
