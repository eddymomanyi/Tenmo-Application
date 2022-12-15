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
    public boolean transfer(BigDecimal amount, int accountFrom, int accountTo) {

        String sql = "INSERT INTO transfer (amount, accountfrom, accountto) VALUES (?,?,?) RETURNING transfer_id";
        Integer transferId;
        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class, amount, accountFrom, accountTo);
        } catch (DataAccessException e) {
            return false;
        }



        String gettingAccountBalance = "SELECT balance FROM account WHERE account_id = ?";

        BigDecimal currentAccountToBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, accountTo);
        String updateAccount = "UPDATE account SET balance = ? WHERE account_id = ? ";
        jdbcTemplate.update(updateAccount, currentAccountToBalance.add(amount), accountTo);

        BigDecimal currentAccountFromBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, accountFrom);
        jdbcTemplate.update(updateAccount, currentAccountFromBalance.subtract(amount), accountFrom);


        return true;
    }
}
