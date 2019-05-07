package com.kaoyan.kaoyandemo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;
import com.kaoyan.kaoyandemo.adapter.SchoolAdapter;
import com.kaoyan.kaoyandemo.info.CommunityInfo;
import com.kaoyan.kaoyandemo.info.SchoolInfo;
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

public class SchoolActivity extends BaseActivity {
    @BindView(R.id.recyclerSchoolView)
    RecyclerView recyclerSchoolView;
    private ArrayList<SchoolInfo> list = new ArrayList();
    private SchoolAdapter schoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
        setToolBar();
        schoolAdapter = new SchoolAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerSchoolView.setLayoutManager(layoutManager);
        recyclerSchoolView.setHasFixedSize(true);
        recyclerSchoolView.setAdapter(schoolAdapter);

        schoolAdapter.setOnItemClickListener(new SchoolAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String schoolid, String schoolname) {
                Intent intent = new Intent();
                intent.putExtra("schoolid", schoolid);
                intent.putExtra("schoolname", schoolname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择学校");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);

        Call<ResponseBody> call = api.schoolList();
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
                            SchoolInfo schoolInfo = new SchoolInfo();
                            schoolInfo.setSchoolId(jsonObject1.optString("schoolId"));
                            schoolInfo.setSchoolName(jsonObject1.getString("schoolName"));
                            list.add(schoolInfo);
                        }
                        schoolAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SchoolActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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