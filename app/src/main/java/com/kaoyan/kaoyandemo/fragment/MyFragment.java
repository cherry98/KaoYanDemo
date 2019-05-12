package com.kaoyan.kaoyandemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.LoginActivity;
import com.kaoyan.kaoyandemo.activity.MyCollectActivity;
import com.kaoyan.kaoyandemo.activity.MyInformationActivity;
import com.kaoyan.kaoyandemo.activity.MyPostActivity;
import com.kaoyan.kaoyandemo.activity.SettingsActivity;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

/**
 * 我的模块
 */
public class MyFragment extends Fragment {


    public MyFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.login)
    TextView login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onResume() {
        if (SharedPreferencesUtils.getLoggedStatus(getContext())) {
            login.setVisibility(View.GONE);
        } else {
            login.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @OnClick(R.id.information)
    public void informationClick() {
        startActivity(new Intent(getContext(), MyInformationActivity.class));
    }

    @OnClick(R.id.collect)
    public void collectClick() {
        startActivity(new Intent(getContext(), MyCollectActivity.class));
    }

    @OnClick(R.id.post)
    public void postClick() {
        startActivity(new Intent(getContext(), MyPostActivity.class));
    }

    @OnClick(R.id.settings)
    public void settingsClick() {
        startActivity(new Intent(getContext(), SettingsActivity.class));
    }

    @OnClick(R.id.login)
    public void loginClick() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
