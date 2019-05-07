package com.kaoyan.kaoyandemo.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.utils.SharedPreferencesUtils;

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
        int num = SharedPreferencesUtils.getAttentionSchoolNum(getContext());
        if (num == 0) {
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.GONE);
            add3.setVisibility(View.GONE);
        } else if (num == 1) {
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.VISIBLE);
            add3.setVisibility(View.GONE);
            add1.setText(SharedPreferencesUtils.getAttentionSchoolName(getContext()));
        } else if (num == 2) {
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.VISIBLE);
            add3.setVisibility(View.VISIBLE);
            String[] names = SharedPreferencesUtils.getAttentionSchoolName(getContext()).split(",");
            add1.setText(names[0]);
            add2.setText(names[1]);
        } else if (num == 3) {
            add1.setVisibility(View.VISIBLE);
            add2.setVisibility(View.VISIBLE);
            add3.setVisibility(View.VISIBLE);
            String[] names = SharedPreferencesUtils.getAttentionSchoolName(getContext()).split(",");
            add1.setText(names[0]);
            add2.setText(names[1]);
            add3.setText(names[3]);
        }
    }

    @OnClick(R.id.add_1)
    public void addClick1() {
    }

    @OnClick(R.id.add_2)
    public void addClick2() {
    }

    @OnClick(R.id.add_3)
    public void addClick3() {
    }
}
