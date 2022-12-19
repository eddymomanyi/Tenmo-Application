package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private int userIdFrom;
    private int userIdTo;
    //@JsonProperty("transferApproved")
    private String status;

    public Transfer(int transferId, BigDecimal amount, int userIdFrom, int userIdTo) {
        this.transferId = transferId;
        this.amount = amount;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
    }

    public Transfer(BigDecimal amount, int accountFrom, int accountTo ){
        this.amount = amount;
        this.userIdFrom = accountFrom;
        this.userIdTo = accountTo;

    }

    public Transfer(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", transfer Status?= " + status + "\n" +
                '}';
    }
}
