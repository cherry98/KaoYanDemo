package com.kaoyan.kaoyandemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostMessageActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        ButterKnife.bind(this);
        setToolBar();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("发布主题");
    }

    @OnClick(R.id.postmessage)
    public void postMessageClick() {
        if (TextUtils.isEmpty(title.getText().toString())) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(title.getText().toString()) || title.getText().length() < 4) {
            Toast.makeText(this, "请输入内容且不少于4个字", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("title", title.getText().toString());
        params.put("content", content.getText().toString());
        params.put("userId", SharedPreferencesUtils.getUserId(this));
        Call<ResponseBody> call = api.setPost(new JSONObject(params).toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(PostMessageActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PostMessageActivity.this, "发布失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
