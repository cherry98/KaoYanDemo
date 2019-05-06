package com.kaoyan.kaoyandemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.activity.BaseActivity;
import com.kaoyan.kaoyandemo.fragment.AttentionFragment;
import com.kaoyan.kaoyandemo.fragment.CommunityFragment;
import com.kaoyan.kaoyandemo.fragment.MyFragment;
import com.kaoyan.kaoyandemo.fragment.SearchFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.attention)
    TextView attention;
    @BindView(R.id.community)
    TextView community;
    @BindView(R.id.my)
    TextView my;

    private SearchFragment searchFragment;
    private AttentionFragment attentionFragment;
    private CommunityFragment communityFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        search.setOnClickListener(this);
        attention.setOnClickListener(this);
        community.setOnClickListener(this);
        my.setOnClickListener(this);
        onClick(search);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search: {
                search.setTextColor(getResources().getColor(R.color.colorPrimary));
                attention.setTextColor(getResources().getColor(R.color.colorAccent));
                community.setTextColor(getResources().getColor(R.color.colorAccent));
                my.setTextColor(getResources().getColor(R.color.colorAccent));
                createSearchFragment();
            }
            break;
            case R.id.attention: {
                search.setTextColor(getResources().getColor(R.color.colorAccent));
                attention.setTextColor(getResources().getColor(R.color.colorPrimary));
                community.setTextColor(getResources().getColor(R.color.colorAccent));
                my.setTextColor(getResources().getColor(R.color.colorAccent));
                createAttentionFragment();
            }
            break;
            case R.id.community: {
                search.setTextColor(getResources().getColor(R.color.colorAccent));
                attention.setTextColor(getResources().getColor(R.color.colorAccent));
                community.setTextColor(getResources().getColor(R.color.colorPrimary));
                my.setTextColor(getResources().getColor(R.color.colorAccent));
                createCommunityFragment();
            }
            break;
            case R.id.my: {
                search.setTextColor(getResources().getColor(R.color.colorAccent));
                attention.setTextColor(getResources().getColor(R.color.colorAccent));
                community.setTextColor(getResources().getColor(R.color.colorAccent));
                my.setTextColor(getResources().getColor(R.color.colorPrimary));
                createMyFragment();
            }
            break;
        }
    }

    private void createMyFragment() {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        myFragment = (MyFragment) fragmentManager.findFragmentByTag("myFragment");
        if (myFragment == null) {
            myFragment = new MyFragment();
        }
        fragmentTransaction.replace(R.id.fragment, myFragment, "myFragment");
        fragmentTransaction.addToBackStack("myFragment");
        fragmentTransaction.commit();
    }

    private void createCommunityFragment() {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        communityFragment = (CommunityFragment) fragmentManager.findFragmentByTag("communityFragment");
        if (communityFragment == null) {
            communityFragment = new CommunityFragment();
        }
        fragmentTransaction.replace(R.id.fragment, communityFragment, "communityFragment");
        fragmentTransaction.addToBackStack("communityFragment");
        fragmentTransaction.commit();
    }

    private void createAttentionFragment() {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        attentionFragment = (AttentionFragment) fragmentManager.findFragmentByTag("attentionFragment");
        if (attentionFragment == null) {
            attentionFragment = new AttentionFragment();
        }
        fragmentTransaction.replace(R.id.fragment, attentionFragment, "attentionFragment");
        fragmentTransaction.addToBackStack("attentionFragment");
        fragmentTransaction.commit();
    }

    private void createSearchFragment() {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        searchFragment = (SearchFragment) fragmentManager.findFragmentByTag("searchFragment");
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }
        fragmentTransaction.replace(R.id.fragment, searchFragment, "searchFragment");
        fragmentTransaction.addToBackStack("searchFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
