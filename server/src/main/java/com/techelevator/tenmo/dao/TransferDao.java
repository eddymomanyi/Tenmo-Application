package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {


    public boolean transfer(BigDecimal amount,int accountFrom, int accountTo);

}
