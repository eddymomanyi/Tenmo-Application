package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {


    public String transfer(BigDecimal amount,int userIdFrom, int userIdTo);

}
