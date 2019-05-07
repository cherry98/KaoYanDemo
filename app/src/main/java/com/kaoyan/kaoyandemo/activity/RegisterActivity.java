package com.kaoyan.kaoyandemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.Api;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.input_username)
    EditText username;
    @BindView(R.id.input_pwd)
    EditText pwd;
    @BindView(R.id.input_name)
    EditText name;
    @BindView(R.id.input_sex)
    EditText sex;
    @BindView(R.id.input_phone)
    EditText phone;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.time_text)
    TextView time;
    @BindView(R.id.major_text)
    TextView major;
    private String majorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.major)
    public void majorClick() {
        startActivityForResult(new Intent(this, MajorActivity.class), 0);
    }

    @OnClick(R.id.time)
    public void timeClick() {
        String[] items = {"2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};
        new AlertDialog.Builder(this)
            .setIcon(null)
            .setTitle(null)
            .setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    time.setText(items[i] + "届");
                }
            }).show();

    }

    @OnClick(R.id.submit)
    public void submitClick() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(this, "请输入您的账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd.getText().toString())) {
            Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(sex.getText().toString())) {
            Toast.makeText(this, "请输入您的性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(time.getText().toString())) {
            Toast.makeText(this, "请选择入学年份", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(major.getText().toString())) {
            Toast.makeText(this, "请选择目标专业", Toast.LENGTH_SHORT).show();
            return;
        }
        register();
    }

    private void register() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://wxooxw.com:8180/kaoyan/kaoyanController/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();
        Api api = retrofit.create(Api.class);
        Map<String, String> params = new HashMap<>();
        params.put("userName", username.getText().toString());
        params.put("pwd", pwd.getText().toString());
        params.put("realName", name.getText().toString());
        params.put("enrollmentYear", time.getText().toString());
        params.put("sex", sex.getText().toString());
        params.put("majorId", majorId);
        params.put("phone", phone.getText().toString());
        Call<ResponseBody> call = api.register(new JSONObject(params).toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("account", username.getText().toString());
                    intent.putExtra("pwd", pwd.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 0) {
                majorId = data.getStringExtra("majorid");
                major.setText(data.getStringExtra("majorname"));
            }
        }
    }
}
