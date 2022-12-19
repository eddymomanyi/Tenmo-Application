package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    public String transfer(BigDecimal amount,int userIdFrom, int userIdTo, String status);

    public List<Transfer> viewMyTransfers(String username);

    public Transfer viewTransferById(int id);

    public String updateTransferStatus(int transferId, String status, String userRequestName);

}
