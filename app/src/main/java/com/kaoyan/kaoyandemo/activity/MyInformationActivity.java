package com.kaoyan.kaoyandemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;

import androidx.appcompat.app.ActionBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInformationActivity extends BaseActivity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.school)
    EditText school;
    @BindView(R.id.major)
    EditText major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        setToolBar();
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
        //TODO
    }

}
