package com.kaoyan.kaoyandemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginout)
    public void loginoutClick() {
        SharedPreferencesUtils.setLoggedStatus(this, false);
        finish();
    }
}
