package com.kaoyan.kaoyandemo.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.CommentActivity;
import com.kaoyan.kaoyandemo.activity.PostMessageActivity;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;
import com.kaoyan.kaoyandemo.info.CommunityInfo;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区模块
 */
public class CommunityFragment extends Fragment {


    public CommunityFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<CommunityInfo> list = new ArrayList();
    private CommunityAdapter communityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCommunityList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        communityAdapter = new CommunityAdapter(getContext(), list, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(communityAdapter);
        communityAdapter.setOnClickCollectListener(new CommunityAdapter.OnClickCollectListener() {
            @Override
            public void click(int position, boolean isAdd) {
                setCollect(position, isAdd);
            }

            @Override
            public void comment(int position) {
                EditText et = new EditText(getContext());
                new AlertDialog.Builder(getContext())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("请输入您要评论的内容")
                    .setView(et)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                        setPostMessage(list.get(position).getPostId(), et.getText().toString());
                    }).show();
            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("postId", list.get(position).getPostId());
                startActivity(intent);
            }
        });
    }

    private void setPostMessage(String postId, String content) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("postId", postId);
        params.put("userId", SharedPreferencesUtils.getUserId(getContext()));
        params.put("content", content);

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.setPostMessage(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void setCollect(int position, boolean isAdd) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        Call<ResponseBody> call;
        if (isAdd) {//添加
            params.put("userId", SharedPreferencesUtils.getUserId(getContext()));
            params.put("collectType", "1");
            params.put("collectTypeId", list.get(position).getPostId());
            params.put("collectId", "");
            String vars = new JSONObject(params).toString();
            call = api.setCollect(vars);
        } else {
            params.put("collectId", list.get(position).getCollectId());
            String vars = new JSONObject(params).toString();
            call = api.deleteCollect(vars);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        getCommunityList();
                        communityAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void getCommunityList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(getContext()));
        params.put("type", "1");
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
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivity(new Intent(getContext(), PostMessageActivity.class));
    }
}
