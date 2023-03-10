package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accounts")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public String createTransfer(@RequestBody Transfer transfer) {
        int userIdFrom = transfer.getUserIdFrom();
        int userIdTo = transfer.getUserIdTo();
        BigDecimal amount = transfer.getAmount();
        String status = "Approved";
        return transferDao.transfer(amount, userIdFrom, userIdTo, status);

    }

    // Allows for users to see their transfer history
    @RequestMapping(path = "/transfer_activity", method = RequestMethod.GET)
    public List<Transfer> viewMyTransfers(Principal principal) {
        String username = principal.getName();
        return transferDao.viewMyTransfers(username);

    }

    // Allows users to find url by ID
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable int id) {
        return transferDao.viewTransferById(id);
    }

    @RequestMapping(path = "/request_transfer", method = RequestMethod.POST)
    public String requestTransfer(@RequestBody Transfer transfer) {
        int userIdFrom = transfer.getUserIdFrom();
        int userIdTo = transfer.getUserIdTo();
        BigDecimal amount = transfer.getAmount();
        String status = "Pending";
        return transferDao.transfer(amount, userIdFrom, userIdTo, status);

    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.PUT)
    public String updateTransfer(@RequestBody Transfer transfer, @PathVariable int transferId, Principal principal) {
        String status = transfer.getStatus();
        String userRequestName = principal.getName();
        return transferDao.updateTransferStatus(transferId, status, userRequestName);


    }


}
