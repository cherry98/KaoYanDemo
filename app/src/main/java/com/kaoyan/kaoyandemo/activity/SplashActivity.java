package com.kaoyan.kaoyandemo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kaoyan.kaoyandemo.MainActivity;
import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 欢迎页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.enter)
    public void enterClick() {
        if (SharedPreferencesUtils.getLoggedStatus(this)) {//已登录
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
