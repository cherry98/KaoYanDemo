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

    @OnClick(R.id.add_1)
    public void addClick1() {
        if ("添加院校".equals(add1.getText().toString())) {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 1);
        } else {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 4);
        }
    }

    @OnClick(R.id.add_2)
    public void addClick2() {
        if ("添加院校".equals(add3.getText().toString())) {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 2);
        } else {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 5);
        }
    }

    @OnClick(R.id.add_3)
    public void addClick3() {
        if ("添加院校".equals(add3.getText().toString())) {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 3);
        } else {
            startActivityForResult(new Intent(getContext(), SchoolActivity.class), 6);
        }
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
            } else if (requestCode == 2) {
                add2.setText(data.getStringExtra("schoolname"));
                str = SharedPreferencesUtils.getAttentionSchoolName(getContext());
                SharedPreferencesUtils.setAttentionSchool(getContext(), str + "," + add2.getText().toString());
            } else if (requestCode == 3) {
                add3.setText(data.getStringExtra("schoolname"));
                str = SharedPreferencesUtils.getAttentionSchoolName(getContext());
                SharedPreferencesUtils.setAttentionSchool(getContext(), str + "," + add3.getText().toString());
            } else if (requestCode == 4) {
                add1.setText(data.getStringExtra("schoolname"));
                String[] names = SharedPreferencesUtils.getAttentionSchoolName(getContext()).split(",");
                if (names.length == 1) {
                    SharedPreferencesUtils.setAttentionSchool(getContext(), add1.getText().toString());
                } else {
                    String str1 = SharedPreferencesUtils.getAttentionSchoolName(getContext());
                    Matcher matcher = Pattern.compile(",").matcher(str1);
                    String old1 = str1.substring(0, matcher.start());
//                    str1.replace(old1)
                }
            } else if (requestCode == 5) {
                add2.setText(data.getStringExtra("schoolname"));
            } else if (requestCode == 6) {
                add3.setText(data.getStringExtra("schoolname"));
            }

            String text1 = add1.getText().toString();
            String text2 = add2.getText().toString();
            String text3 = add3.getText().toString();
        }
    }
}
