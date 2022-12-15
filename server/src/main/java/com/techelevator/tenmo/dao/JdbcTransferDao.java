package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public String transfer(BigDecimal amount, int userIdFrom, int userIdTo) {
        if (userIdFrom == userIdTo) {

            return "User cannot send money to themselves, No infinite money glitch, sorry!";
        }
        String sql = "INSERT INTO transfer (amount, accountfrom, accountto) VALUES (?,?,?) RETURNING transfer_id";
        Integer transferId;

        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class, amount, userIdFrom, userIdTo);
        } catch (DataAccessException e) {
            return "Transfer not successful, Please try again!";
        }



        String gettingAccountBalance = "SELECT balance FROM account WHERE account_id = ?";

        BigDecimal currentAccountToBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdTo);
        String updateAccount = "UPDATE account SET balance = ? WHERE account_id = ? ";
        jdbcTemplate.update(updateAccount, currentAccountToBalance.add(amount), userIdTo);

        BigDecimal currentAccountFromBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdFrom);
        jdbcTemplate.update(updateAccount, currentAccountFromBalance.subtract(amount), userIdFrom);


        return "Transfer successful";
    }
}
