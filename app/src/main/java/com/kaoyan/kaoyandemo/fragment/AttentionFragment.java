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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.SchoolActivity;
import com.kaoyan.kaoyandemo.adapter.SearchAdapter;
import com.kaoyan.kaoyandemo.info.SearchInfo;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * 关注
 */
public class AttentionFragment extends Fragment {


    public AttentionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.add_1)
    Button add1;
    @BindView(R.id.add_2)
    Button add2;
    @BindView(R.id.add_3)
    Button add3;
    @BindView(R.id.reset)
    Button reset;
    private String collectId1 = "", collectId2 = "", collectId3 = "";

    private ArrayList<SearchInfo> list = new ArrayList<>();
    private SearchAdapter searchAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_attention, container, false);
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
        getAllCollectSchool();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAllCollectSchool() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(getContext()));
        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.getAllCollectSchool(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        int length = jsonArray.length();
                        if (length == 1) {//只关注了一个学校
                            add1.setText(jsonArray.optJSONObject(0).getString("schoolName"));
                            collectId1 = jsonArray.optJSONObject(0).getString("collectId");
                        } else if (length == 2) {
                            add1.setText(jsonArray.optJSONObject(0).getString("schoolName"));
                            collectId1 = jsonArray.optJSONObject(0).getString("collectId");
                            add2.setText(jsonArray.optJSONObject(1).getString("schoolName"));
                            collectId2 = jsonArray.optJSONObject(1).getString("collectId");
                        } else if (length == 3) {
                            add1.setText(jsonArray.optJSONObject(0).getString("schoolName"));
                            collectId1 = jsonArray.optJSONObject(0).getString("collectId");
                            add2.setText(jsonArray.optJSONObject(1).getString("schoolName"));
                            collectId2 = jsonArray.optJSONObject(1).getString("collectId");
                            add3.setText(jsonArray.optJSONObject(2).getString("schoolName"));
                            collectId3 = jsonArray.optJSONObject(2).getString("collectId");
                        }
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
        call = api.getAllCollectSchoolKaoyanData(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        if (!list.isEmpty()) {
                            list.clear();
                        }
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

            }
        });
    }

    @OnClick(R.id.add_1)
    public void addClick1() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 1);
    }

    @OnClick(R.id.add_2)
    public void addClick2() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 2);
    }

    @OnClick(R.id.add_3)
    public void addClick3() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String schoolname = data.getStringExtra("schoolname");
            String schoolid = data.getStringExtra("schoolid");
            if (requestCode == 1) {//设置第一个按钮的关注结果
                add1.setText(schoolname);
                setCollect(schoolid, collectId1);
            } else if (requestCode == 2) {
                add2.setText(schoolname);
                setCollect(schoolid, collectId2);
            } else if (requestCode == 3) {
                add3.setText(schoolname);
                setCollect(schoolid, collectId3);
            }
        }
    }

    private void setCollect(String collectTypeId, String collectId) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(getContext()));
        params.put("collectType", "2");
        params.put("collectTypeId", collectTypeId);
        params.put("collectId", collectId);
        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.setCollect(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
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
}
