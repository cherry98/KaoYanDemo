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
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.adapter.CommentAdapter;
import com.kaoyan.kaoyandemo.adapter.CommunityAdapter;
import com.kaoyan.kaoyandemo.info.CommentInfo;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 帖子评论列表
 */
public class CommentActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String postId;
    private ArrayList<CommentInfo> list = new ArrayList<>();//存储接口返回的数据
    private CommentAdapter commentAdapter;//适配器
    //adapter是数据与ui之间的桥梁，它把后台数据与前端ui连接到一起，是一个展示数据的载体

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        postId = getIntent().getStringExtra("postId");
        ButterKnife.bind(this);//绑定ButterKnife  这个控件干嘛用的可自行百度
        setToolBar();

        commentAdapter = new CommentAdapter(this, list);//实例化适配器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);//设置适配器
        getCommentList();
    }

    private void getCommentList() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("postId", postId);

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.messageList(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {//返回成功值处理返回结果
                        JSONArray jsonArray = jsonObject.optJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            CommentInfo commentInfo = new CommentInfo();
                            commentInfo.setContent(jsonObject1.getString("content"));
                            commentInfo.setCreate_time(jsonObject1.getString("create_time"));
                            commentInfo.setRealName(jsonObject1.getString("realName"));
                            list.add(commentInfo);
                        }
                        commentAdapter.notifyDataSetChanged();
                    } else {//返回失败值给出提示
                        Toast.makeText(CommentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的评论");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
