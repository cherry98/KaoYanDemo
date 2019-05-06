package com.kaoyan.kaoyandemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.kaoyan.kaoyandemo.R;

import androidx.appcompat.app.ActionBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    }
}
