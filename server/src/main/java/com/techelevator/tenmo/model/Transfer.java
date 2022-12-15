package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String transferStatus;
    private BigDecimal amount;
    private int userIdFrom;
    private int userIdTo;

    public Transfer(BigDecimal amount, int accountFrom, int accountTo ){
        this.amount = amount;
        this.userIdFrom = accountFrom;
        this.userIdTo = accountTo;
    }


    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
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

    public void setUserIdFrom(int accountFrom) {
        this.userIdFrom = accountFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(int accountTo) {
        this.userIdTo = accountTo;
    }
}
