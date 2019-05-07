package com.kaoyan.kaoyandemo.activity;

import androidx.appcompat.app.ActionBar;
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

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.SearchAdapter;
import com.kaoyan.kaoyandemo.info.SearchInfo;
import com.kaoyan.kaoyandemo.utils.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.school)
    TextView school;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.major)
    TextView major;

    private ArrayList<SearchInfo> list = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private String typeid = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setToolBar();
        searchAdapter = new SearchAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
    }

    @OnClick(R.id.search)
    public void searchClick() {
        if (TextUtils.isEmpty(input.getText().toString())) {
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }
        getSearchList();
    }

    @OnClick(R.id.area)
    public void areaClick() {
        typeid = "3";
        school.setBackgroundResource(R.drawable.button_normal);
        area.setBackgroundResource(R.drawable.button_pressed);
        major.setBackgroundResource(R.drawable.button_normal);
    }

    @OnClick(R.id.school)
    public void schoolClick() {
        typeid = "1";
        school.setBackgroundResource(R.drawable.button_pressed);
        area.setBackgroundResource(R.drawable.button_normal);
        major.setBackgroundResource(R.drawable.button_normal);
    }

    @OnClick(R.id.major)
    public void majorClick() {
        typeid = "2";
        school.setBackgroundResource(R.drawable.button_normal);
        area.setBackgroundResource(R.drawable.button_normal);
        major.setBackgroundResource(R.drawable.button_pressed);
    }

    private void getSearchList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("typeid", typeid);
        params.put("keyword", input.getText().toString());

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.kaoyanList(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
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
                        Toast.makeText(SearchActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("搜索");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
