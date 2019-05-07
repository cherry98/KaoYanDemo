package com.kaoyan.kaoyandemo.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.activity.SchoolActivity;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttentionFragment extends Fragment {


    public AttentionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.add_1)
    Button add1;
    @BindView(R.id.add_2)
    Button add2;
    @BindView(R.id.add_3)
    Button add3;
    @BindView(R.id.reset)
    Button reset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_attention, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (TextUtils.isEmpty(SharedPreferencesUtils.getAttentionSchoolName(getContext()))) {
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.GONE);
            add3.setVisibility(View.GONE);
        } else {
            String[] names = SharedPreferencesUtils.getAttentionSchoolName(getContext()).split(",");
            if (names.length == 0) {
                add1.setVisibility(View.VISIBLE);
                add2.setVisibility(View.GONE);
                add3.setVisibility(View.GONE);
            } else if (names.length == 1) {
                add1.setVisibility(View.VISIBLE);
                add2.setVisibility(View.VISIBLE);
                add3.setVisibility(View.GONE);
                add1.setText(SharedPreferencesUtils.getAttentionSchoolName(getContext()));
            } else if (names.length == 2) {
                add1.setVisibility(View.VISIBLE);
                add2.setVisibility(View.VISIBLE);
                add3.setVisibility(View.VISIBLE);
                add1.setText(names[0]);
                add2.setText(names[1]);
            } else if (names.length == 3) {
                reset.setVisibility(View.VISIBLE);
                add1.setVisibility(View.VISIBLE);
                add2.setVisibility(View.VISIBLE);
                add3.setVisibility(View.VISIBLE);
                add1.setText(names[0]);
                add2.setText(names[1]);
                add3.setText(names[2]);
            }
        }


        super.onResume();
    }

    @OnClick(R.id.reset)
    public void resetCLick() {
        add1.setText("添加院校");
        add2.setText("添加院校");
        add3.setText("添加院校");
        add1.setVisibility(View.VISIBLE);
        add2.setVisibility(View.GONE);
        add3.setVisibility(View.GONE);
        add1.setClickable(true);
        add2.setClickable(true);
        add3.setClickable(true);
        reset.setVisibility(View.GONE);
    }

    @OnClick(R.id.add_1)
    public void addClick1() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 1);
    }

    @OnClick(R.id.add_2)
    public void addClick2() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 2);
    }

    @OnClick(R.id.add_3)
    public void addClick3() {
        startActivityForResult(new Intent(getContext(), SchoolActivity.class), 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String str;
            if (requestCode == 1) {
                add1.setText(data.getStringExtra("schoolname"));
                str = add1.getText().toString();
                SharedPreferencesUtils.setAttentionSchool(getContext(), str);//no
                add1.setClickable(false);
                reset.setVisibility(View.GONE);
            } else if (requestCode == 2) {
                add2.setText(data.getStringExtra("schoolname"));
                str = SharedPreferencesUtils.getAttentionSchoolName(getContext());
                SharedPreferencesUtils.setAttentionSchool(getContext(), str + "," + add2.getText().toString());
                add2.setClickable(false);
                reset.setVisibility(View.GONE);
            } else if (requestCode == 3) {
                add3.setText(data.getStringExtra("schoolname"));
                str = SharedPreferencesUtils.getAttentionSchoolName(getContext());
                SharedPreferencesUtils.setAttentionSchool(getContext(), str + "," + add3.getText().toString());
                add3.setClickable(false);
                reset.setVisibility(View.VISIBLE);
            }
        }
    }
}
