package com.example.nicefragment;

import android.view.View;

import com.example.nicefragment.base.LazyFragment;

public class UseFragment extends LazyFragment {
    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onFragmentLoadData() {
        super.onFragmentLoadData();
        //TODO:具体加载数据操作
    }
}
