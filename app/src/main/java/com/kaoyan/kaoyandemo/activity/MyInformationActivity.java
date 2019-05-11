package com.kaoyan.kaoyandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
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

/**
 * 个人资料页面
 */
public class MyInformationActivity extends BaseActivity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.school)
    TextView school;
    @BindView(R.id.major)
    TextView major;
    private String majorId, schoolid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        setToolBar();
        getuserData();
    }

    @OnClick(R.id.school)
    public void schoolClick() {
        startActivityForResult(new Intent(this, SchoolActivity.class), 0);
    }

    @OnClick(R.id.major)
    public void majorClick() {
        startActivityForResult(new Intent(this, MajorActivity.class), 1);
    }

    private void getuserData() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(this));

        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.userData(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        jsonObject = jsonObject.optJSONObject("result");
                        name.setText(jsonObject.optString("realName"));
                        sex.setText(jsonObject.optString("sex"));
                        major.setText(jsonObject.optString("majorName"));
                        school.setText(jsonObject.optString("schoolName"));
                        phone.setText(jsonObject.optString("phone"));
                    } else {
                        Toast.makeText(MyInformationActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
        actionBar.setTitle("个人资料");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.change)
    public void changeClick() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sex.getText().toString())) {
            Toast.makeText(this, "请输入您的性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(this, "请输入您的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(school.getText().toString())) {
            Toast.makeText(this, "请输入您的学校", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(major.getText().toString())) {
            Toast.makeText(this, "请输入您的专业", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userId", SharedPreferencesUtils.getUserId(this));
        params.put("realName", name.getText().toString());
        params.put("phone", phone.getText().toString());
        params.put("sex", sex.getText().toString());
        if (!TextUtils.isEmpty(schoolid)) {
            params.put("schoolId", schoolid);
        }
        if (!TextUtils.isEmpty(majorId)) {
            params.put("majorId", majorId);
        }
        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.editUserData(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        Toast.makeText(MyInformationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyInformationActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 0) {
                schoolid = data.getStringExtra("schoolid");
                school.setText(data.getStringExtra("schoolname"));
            } else if (requestCode == 1) {
                majorId = data.getStringExtra("majorid");
                major.setText(data.getStringExtra("majorname"));
            }
        }
    }
}
