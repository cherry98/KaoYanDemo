package com.kaoyan.kaoyandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.MainActivity;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.Api;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
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
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_phone)
    EditText phone;
    @BindView(R.id.input_pwd)
    EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void loginClick() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(this, "请输入您的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd.getText().toString())) {
            Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        login();
    }

    private void login() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userName", phone.getText().toString());
        params.put("pwd", pwd.getText().toString());


        String vars = new JSONObject(params).toString();
        Call<ResponseBody> call = api.login(vars);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = new String(response.body().bytes());

                    JSONObject jsonObject = new JSONObject(str);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        String userId = jsonObject.optJSONObject("result").getString("userId");
                        SharedPreferencesUtils.setUserId(LoginActivity.this, userId);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtils.setLoggedStatus(LoginActivity.this, true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.register)
    public void registerClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0 && data != null) {
            phone.setText(data.getStringExtra("account"));
            pwd.setText(data.getStringExtra("pwd"));
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
