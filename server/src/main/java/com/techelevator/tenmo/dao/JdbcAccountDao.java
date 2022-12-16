package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@Component

public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(String username) {
        String sql = "SELECT balance FROM account a JOIN tenmo_user t ON t.user_id = a.user_id WHERE t.username = ?";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, username);

        return balance;
    }

    public Account getAccount(String username){
        String sql = "SELECT account_id, a.user_id, balance FROM account a JOIN tenmo_user t ON a.user_id = t.user_id WHERE username=? ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        Account account = new Account();
        if(rowSet.next()){
            account = mapRowToAccount(rowSet);
        }

        return account;
    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccount_id(rowSet.getInt("account_id"));
        account.setUser_id(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
