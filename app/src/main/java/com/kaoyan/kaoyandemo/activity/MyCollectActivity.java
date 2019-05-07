package com.kaoyan.kaoyandemo.activity;


import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;
import com.kaoyan.kaoyandemo.info.CommunityInfo;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCollectActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<CommunityInfo> list = new ArrayList();
    private CommunityAdapter communityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        setToolBar();
        communityAdapter = new CommunityAdapter(this, list, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(communityAdapter);
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的收藏");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(MyCollectActivity.this));

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.collectList(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String str = new String(response.body().bytes());
                        JSONObject jsonObject = new JSONObject(str);
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                CommunityInfo communityInfo = new CommunityInfo();
                                communityInfo.setContent(jsonObject1.getString("content"));
                                communityInfo.setCollectId(jsonObject1.getString("collectId"));
                                communityInfo.setTitle(jsonObject1.getString("title"));
                                list.add(communityInfo);
                            }
                            communityAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MyCollectActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
