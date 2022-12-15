package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accounts")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public String createTransfer(@RequestBody Transfer transfer){
        int userIdFrom = transfer.getUserIdFrom();
        int userIdTo = transfer.getUserIdFrom();
        BigDecimal amount = transfer.getAmount();

        return transferDao.transfer(amount, userIdFrom, userIdTo);



    }


}
