package com.haoxt.mpos.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.FragmentTag;
import com.haoxt.mpos.widget.MyDialog;

public class SignaturePopup extends MyDialog implements View.OnClickListener {

    private final PayActivity mActivity;
    private LinearLayout mLayout;

    public SignaturePopup(PayActivity activity) {
        super(activity);
        mActivity = activity;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mLayout = (LinearLayout) inflater.inflate(R.layout.popup_signature, null);

        TextView close_tv = mLayout.findViewById(R.id.close_tv);
        TextView rewrite_tv = mLayout.findViewById(R.id.rewrite_tv);
        TextView confirm_tv = mLayout.findViewById(R.id.confirm_tv);
        close_tv.setOnClickListener(this);
        rewrite_tv.setOnClickListener(this);
        confirm_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_tv:
                if (isShowing())
                    cancel();
                break;
            case R.id.rewrite_tv:
            case R.id.confirm_tv:
                if (isShowing())
                    cancel();
                mActivity.replaceFragment(FragmentTag.SALES_SLIP_FRAGMENT);
                break;
        }
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
        this.setCancelable(false);
        this.setContentView(mLayout);
    }
}
