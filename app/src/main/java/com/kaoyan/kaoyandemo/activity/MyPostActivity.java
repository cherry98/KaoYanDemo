package com.kaoyan.kaoyandemo.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;
import com.kaoyan.kaoyandemo.info.CommunityInfo;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的发帖
 */
public class MyPostActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<CommunityInfo> list = new ArrayList();
    private CommunityAdapter communityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        ButterKnife.bind(this);
        setToolBar();
        communityAdapter = new CommunityAdapter(MyPostActivity.this, list, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyPostActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(communityAdapter);
        getCommunityList();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的发帖");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getCommunityList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(MyPostActivity.this));
        params.put("type", "2");
        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.postList(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
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
                            communityInfo.setCheck(!TextUtils.isEmpty(jsonObject1.getString("collectId")));
                            communityInfo.setPostId(jsonObject1.getString("postId"));
                            communityInfo.setTitle(jsonObject1.getString("title"));
                            communityInfo.setRealName(jsonObject1.getString("realName"));
                            list.add(communityInfo);
                        }
                        communityAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MyPostActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
