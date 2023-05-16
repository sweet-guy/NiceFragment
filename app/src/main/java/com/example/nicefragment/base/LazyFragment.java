package com.example.nicefragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//懒加载模式性能优化架构
public abstract class LazyFragment extends Fragment {
    View rootView;
    boolean isViewCreated=false;
    boolean isCurrentVisibaleState=false;
    private long loadTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null)
        {
            rootView=inflater.inflate(getLayoutId(),container,false);
        }
        isViewCreated=true;
        initView(rootView);
        if(getUserVisibleHint())
        {
            dispatchUserVisibleHint(true);
        }
        return rootView;
    }

    protected abstract void initView(View rootView);

    protected abstract int getLayoutId();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //TODO:控制请求时间
        if(System.currentTimeMillis()-loadTime>1000*60)
        {
            if(isViewCreated)
            {
                if(!isCurrentVisibaleState&&isVisibleToUser)
                {
                    dispatchUserVisibleHint(true);
                }
                else if(isCurrentVisibaleState&&!isVisibleToUser)
                {
                    dispatchUserVisibleHint(false);
                }
            }
        }
    }

    /**
     * 控制是否能够操作当前界面
     * @param isVisible
     */
    public void dispatchUserVisibleHint(boolean isVisible)
    {
        if(isCurrentVisibaleState==isVisible)
        {
            return;
        }
        isCurrentVisibaleState=isVisible;
        if(isVisible)
        {
            onFragmentLoadData();
        }
        else
        {
            onFragmentStopLoadData();
        }
    }
    //TODO:取消数据加载
    public void onFragmentLoadData()
    {

    }
    //TODO:加载数据
    public void onFragmentStopLoadData()
    {
        loadTime = System.currentTimeMillis();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isCurrentVisibaleState&&getUserVisibleHint())
        {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isCurrentVisibaleState&&!getUserVisibleHint())
        {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
