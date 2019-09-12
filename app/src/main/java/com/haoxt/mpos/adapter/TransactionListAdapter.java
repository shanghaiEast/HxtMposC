package com.haoxt.mpos.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.ListTransaction;

import java.util.List;

/**
 * 用户adapter
 *
 * @author baowen
 */
public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private Context context;
    private List<ListTransaction> data;
    private OnItemClickListener mOnItemClickListener;

    public TransactionListAdapter(Context context, List<ListTransaction> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_list, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ListTransaction transaction = data.get(position);
        if (transaction.payResult == 0) {
            holder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick();
                }
            });
        }
        setPayIcon(holder.mPayTypeIv, transaction.payType);
        setText(holder.mPayResultTv, transaction.payResult);
        holder.mPayMoneyTv.setText(transaction.payMoney);
        holder.mPayTimeTv.setText(transaction.payTime);
        holder.mPayNumTv.setText(transaction.payNum);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setPayIcon(ImageView imageView, int payType) {
        switch (payType) {
            case PayType.ALI_PAY:
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.ali_pay));
                break;
            case PayType.WE_CHAT:
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.we_chat));
                break;
            case PayType.ORDINARY:
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.card_icon));
                break;
        }
    }

    public static void setText(TextView textView, int payResult) {
        switch (payResult) {
            case 0:
                textView.setText(R.string.deals_done);
                break;
            case 1:
                textView.setText(R.string.failure_deal);
                break;
            case 2:
                textView.setText(R.string.unknown_transaction);
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPayResultTv, mPayMoneyTv, mPayTimeTv, mPayNumTv;
        private ImageView mPayTypeIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mPayTypeIv = itemView.findViewById(R.id.pay_type_iv);
            mPayResultTv = itemView.findViewById(R.id.pay_result_tv);
            mPayMoneyTv = itemView.findViewById(R.id.pay_money_tv);
            mPayTimeTv = itemView.findViewById(R.id.pay_time_tv);
            mPayNumTv = itemView.findViewById(R.id.pay_num_tv);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

}