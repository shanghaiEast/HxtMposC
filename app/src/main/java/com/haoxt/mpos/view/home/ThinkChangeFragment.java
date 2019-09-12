package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.FragmentTag;

import tft.mpos.library.base.BaseFragment;

public class ThinkChangeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mScanningFrameIv;

    //单例
    public static ThinkChangeFragment newInstance() {
        return ThinkChangeFragment.ThinkChangeFragmentFactory.thinkChangeFragment;
    }

    private static final class ThinkChangeFragmentFactory {
        public static final ThinkChangeFragment thinkChangeFragment = new ThinkChangeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_think_change);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    @Override
    public void initView() {
        mScanningFrameIv = findView(R.id.scanning_frame_iv);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mScanningFrameIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scanning_frame_iv:
                ((PayActivity) getActivity()).replaceFragment(FragmentTag.SWIPE_RESULTS_FRAGMENT);
                break;
        }
    }
}
