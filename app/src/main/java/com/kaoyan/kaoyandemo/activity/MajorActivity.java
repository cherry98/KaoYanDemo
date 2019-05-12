package com.kaoyan.kaoyandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.MajorAdapter;
import com.kaoyan.kaoyandemo.info.SchoolInfo;
import com.kaoyan.kaoyandemo.utils.Api;

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

/**
 * 专业信息列表
 */
public class MajorActivity extends BaseActivity {

    @BindView(R.id.recyclerMajorView)
    RecyclerView recyclerMajorView;
    private ArrayList<SchoolInfo> list = new ArrayList();
    private MajorAdapter majorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        ButterKnife.bind(this);
        setToolBar();
        majorAdapter = new MajorAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMajorView.setLayoutManager(layoutManager);
        recyclerMajorView.setHasFixedSize(true);
        recyclerMajorView.setAdapter(majorAdapter);
        majorAdapter.setOnItemClickListener((majorid, majorname) -> {
            Intent intent = new Intent();
            intent.putExtra("majorid", majorid);
            intent.putExtra("majorname", majorname);
            setResult(RESULT_OK, intent);
            finish();
        });
        getMyCollectList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择专业");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getMyCollectList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("schoolId", "0");
        String var = new JSONObject(params).toString();
        Call<ResponseBody> call = api.majorList(var);
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
                            schoolInfo.setSchoolId(jsonObject1.optString("majorId"));
                            schoolInfo.setSchoolName(jsonObject1.getString("majorName"));
                            list.add(schoolInfo);
                        }
                        majorAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MajorActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
