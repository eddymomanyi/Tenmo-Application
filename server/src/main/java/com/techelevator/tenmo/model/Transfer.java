package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private int userIdFrom;
    private int userIdTo;
    @JsonProperty("transferSuccess")
    private boolean transferSuccess;

    public Transfer(BigDecimal amount, int accountFrom, int accountTo ){
        this.amount = amount;
        this.userIdFrom = accountFrom;
        this.userIdTo = accountTo;
    }

    public Transfer(){

    }

    public boolean transferSuccess() {
        return transferSuccess();
    }

    public void setTransferSuccess(boolean transferSuccess) {
        this.transferSuccess = transferSuccess;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    @Override
    public String toString() {
        return "Transfer{" + "\n" +
                "transfer_id= " + transferId + "\n" +
                ", amount= " + amount + "\n" +
                ", userIdFrom= " + userIdFrom + "\n" +
                ", userIdTo= " + userIdTo + "\n" +
                ", transfer Success?= " + transferSuccess + "\n" +
                '}';
    }
}
