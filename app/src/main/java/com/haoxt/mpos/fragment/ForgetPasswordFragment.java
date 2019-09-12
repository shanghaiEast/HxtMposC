package com.haoxt.mpos.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haoxt.mpos.R;

import tft.mpos.library.base.BaseFragment;

public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackIv;

    public static ForgetPasswordFragment newInstance() {
        return new ForgetPasswordFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_forget_password);
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
        mBackIv = view.findViewById(R.id.back_iv);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                getActivity().onBackPressed();
                break;
        }
    }
}
