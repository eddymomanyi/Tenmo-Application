package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public String transfer(BigDecimal amount, int userIdFrom, int userIdTo) {

        if (userIdFrom == userIdTo ) {
            return "User cannot send money to themselves, No infinite money glitch, sorry!";
        }

        String gettingAccountBalance = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal currentAccountToBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdTo);
        BigDecimal currentAccountFromBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdFrom);

        if(amount.compareTo(currentAccountFromBalance) >= 0 ){
            return "Transfer cannot be processed. Not enough funds";
        }
        if(amount.compareTo(BigDecimal.valueOf(0)) <= 0){
            return "Transfer cannot be processed. Cannot send 0 or fewer TE Bucks.";
        }

        String sql = "INSERT INTO transfer (amount, userIdFrom, userIdTo, transferSuccess) VALUES (?,?,?, ?) RETURNING transfer_id";
        Integer transferId;

        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class, amount, userIdFrom, userIdTo, true);
        } catch (DataAccessException e) {
            return "Transfer not successful, Please try again!";
        }

        //Updating Account balances
        String updateAccount = "UPDATE account SET balance = ? WHERE user_id = ? ";
        jdbcTemplate.update(updateAccount, currentAccountToBalance.add(amount), userIdTo);
        jdbcTemplate.update(updateAccount, currentAccountFromBalance.subtract(amount), userIdFrom);


        return "Transfer successful";
    }


    public List<Transfer> viewMyTransfers(String username){
        String sqlForUserId = "SELECT user_id FROM tenmo_user WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlForUserId, Integer.class, username);
        String sqlForTransfers = "SELECT * FROM transfer WHERE userIdFrom =? OR userIdTo= ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlForTransfers, userId, userId);
        List<Transfer> transfers = new ArrayList<>();
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        System.out.println(transfers);
        return transfers;
    }

    public Transfer viewTransferById(int id) {
        String sql ="SELECT * FROM transfer WHERE transfer_id = ?";
       SqlRowSet transferInfo = jdbcTemplate.queryForRowSet(sql, id);
      Transfer transfer = new Transfer();
       if(transferInfo.next()) {
           transfer = mapRowToTransfer(transferInfo);
       } return transfer;

       }


    private Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferSuccess(rowSet.getBoolean("transferSuccess"));
        transfer.setUserIdFrom(rowSet.getInt("userIdFrom"));
        transfer.setUserIdTo(rowSet.getInt("userIdTo"));
        return transfer;
    }



}
