package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;

import tft.mpos.library.base.BaseFragment;

public class SwipeResultsFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackIv;
    private Button mQueryBt;
    private SignaturePopup mSignaturePopup;

    //单例
    public static SwipeResultsFragment newInstance() {
        return SwipeResultsFragment.SwipeResultsFragmentFactory.swipeResultsFragment;
    }

    private static final class SwipeResultsFragmentFactory {
        public static final SwipeResultsFragment swipeResultsFragment = new SwipeResultsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_swipe_result);
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
        mBackIv = findView(R.id.back_iv);
        mQueryBt = findView(R.id.query_bt);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
        mQueryBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                getActivity().onBackPressed();
                break;
            case R.id.query_bt:
                showPop();
                break;
        }
    }

    private void showPop() {
        if (mSignaturePopup != null) {
            mSignaturePopup = null;
        }
        mSignaturePopup = new SignaturePopup(((PayActivity) getActivity()));
        mSignaturePopup.show();
    }
}
