package com.haoxt.mpos.entity;

public class ListTransaction {

    public int payType;
    public int payResult;
    public String payMoney;
    public String payTime;
    public String payNum;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayResult() {
        return payResult;
    }

    public void setPayResult(int payResult) {
        this.payResult = payResult;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }
}