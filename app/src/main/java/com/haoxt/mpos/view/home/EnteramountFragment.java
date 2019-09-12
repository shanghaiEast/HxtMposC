package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.FragmentTag;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.widget.MyDialog;

import tft.mpos.library.base.BaseFragment;

public class EnteramountFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private EditText mNumberEt;
    private EditText mAuthCodeEt;
    private TextView bankCardTv;
    private TextView mBankCardTv;
    private MyDialog mDialog;
    private RelativeLayout mAliPayRl;
    private RelativeLayout mWeChatRl;
    private Button mPayBt;


    //单例
    public static EnteramountFragment newInstance() {
        return EnteramountFragmentFactory.enteramountFragment;
    }

    public static final class EnteramountFragmentFactory {
        public static final EnteramountFragment enteramountFragment = new EnteramountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_enteramount);
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
        mTitleTv = findView(R.id.title_tv);
        mNumberEt = findView(R.id.number_et);
        mAuthCodeEt = findView(R.id.auth_code_et);
        mBankCardTv = findView(R.id.bank_card_tv);
        mAliPayRl = findView(R.id.ali_pay_rl);
        mWeChatRl = findView(R.id.we_chat_rl);
        mPayBt = findView(R.id.pay_bt);
    }

    @Override
    public void initData() {
        int payType = ((PayActivity) getActivity()).getPayType();
        setLayout(payType);
    }

    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
        mBankCardTv.setOnClickListener(this);
        mPayBt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                getActivity().onBackPressed();
                break;
            case R.id.bank_card_tv:
                showPop();
                break;
            case R.id.pay_bt:
                PayActivity activity = (PayActivity) getActivity();
                if (activity.getPayType() == PayType.ALI_PAY || activity.getPayType() == PayType.WE_CHAT)
                    activity.replaceFragment(FragmentTag.THINK_CHANGE_FRAGMENT);
                else
                    activity.replaceFragment(FragmentTag.CONNECT_POS_FRAGMENT);
                break;
            case R.id.card_payment_bt:
                dismissPop();
                ((PayActivity) getActivity()).replaceFragment(FragmentTag.CONNECT_POS_FRAGMENT);
                break;
            case R.id.cancel_tv:
                dismissPop();
                break;
        }
    }

    private void showPop() {
        if (mDialog == null)
            mDialog = new MyDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.popup, null);

        Button payment = layout.findViewById(R.id.card_payment_bt);
        TextView cancel = layout.findViewById(R.id.cancel_tv);
        cancel.setOnClickListener(this);
        payment.setOnClickListener(this);
        mDialog.show();
        mDialog.setCancelable(false);
        mDialog.setContentView(layout);
    }

    private void dismissPop() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }

    public void setLayout(int payType) {
        int visibility = -1;
        switch (payType) {
            case PayType.ALI_PAY:
            case PayType.WE_CHAT:
                mTitleTv.setText(R.string.we_chat);
                visibility = View.VISIBLE;
                break;
            case PayType.ORDINARY:
                mTitleTv.setText(R.string.ordinary);
                visibility = View.GONE;
                break;
            case PayType.SUPER:
                mTitleTv.setText(R.string.superduty);
                visibility = View.GONE;
                break;
            case PayType.QUICK_PASS:
                mTitleTv.setText(R.string.quick_pass);
                visibility = View.GONE;
                break;
        }
        if (visibility != -1) {
            mAliPayRl.setVisibility(visibility);
            mWeChatRl.setVisibility(visibility);
        }
    }
}
