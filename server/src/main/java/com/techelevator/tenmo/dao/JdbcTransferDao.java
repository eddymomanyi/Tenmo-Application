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
    private JdbcUserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userDao = new JdbcUserDao(jdbcTemplate);
    }


    @Override
    public String transfer(BigDecimal amount, int userIdFrom, int userIdTo, String status) {

        if (userIdFrom == userIdTo) {
            return "User cannot send or request money to themselves, sorry!";
        }

        String gettingAccountBalance = "SELECT balance FROM account WHERE user_id = ?";
        BigDecimal currentAccountToBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdTo);
        BigDecimal currentAccountFromBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, userIdFrom);

        if (amount.compareTo(currentAccountFromBalance) >= 0) {
            return "Transfer cannot be processed. Not enough funds";
        }
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return "Transfer cannot be processed. Cannot send 0 or fewer TE Bucks.";
        }

        String sql = "INSERT INTO transfer (amount, userIdFrom, userIdTo, status) VALUES (?,?,?, ?) RETURNING transfer_id";
        Integer transferId;

        try {
            transferId = jdbcTemplate.queryForObject(sql, Integer.class, amount, userIdFrom, userIdTo, status);
        } catch (DataAccessException e) {
            return "Transfer process not successful, Please try again!";
        }

        if (status.equals("Approved")) {
            String updateAccount = "UPDATE account SET balance = ? WHERE user_id = ? ";
            jdbcTemplate.update(updateAccount, currentAccountToBalance.add(amount), userIdTo);
            jdbcTemplate.update(updateAccount, currentAccountFromBalance.subtract(amount), userIdFrom);
            return "Transfer successful";
        } else {
            return "Transfer request successful";
        }


    }

    public String updateTransferStatus(int transferId, String status, String userRequestName) {
        String sql = "UPDATE transfer SET status = ? WHERE transfer_id = ?";
        int userRequestID = userDao.findIdByUsername(userRequestName);
        Transfer transfer = viewTransferById(transferId);
        try{
            if(transfer.getUserIdTo() == userRequestID){
                jdbcTemplate.update(sql, status, transferId);
            }else {
                return "Invalid request";
            }
        }catch(DataAccessException e){
            return "Unable to update transfer status";
        }

        if(status.equals("Approved") ){

            String gettingAccountBalance = "SELECT balance FROM account WHERE user_id = ?";
            BigDecimal currentAccountToBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, transfer.getUserIdTo());
            BigDecimal currentAccountFromBalance = jdbcTemplate.queryForObject(gettingAccountBalance, BigDecimal.class, transfer.getUserIdFrom());

            String updateAccount = "UPDATE account SET balance = ? WHERE user_id = ? ";
            jdbcTemplate.update(updateAccount, currentAccountToBalance.add(transfer.getAmount()), transfer.getUserIdTo());
            jdbcTemplate.update(updateAccount, currentAccountFromBalance.subtract(transfer.getAmount()), transfer.getUserIdFrom());
        }
        return "Transfer status successfully updated";

    }


    public List<Transfer> viewMyTransfers(String username) {
        String sqlForUserId = "SELECT user_id FROM tenmo_user WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sqlForUserId, Integer.class, username);
        String sqlForTransfers = "SELECT * FROM transfer WHERE userIdFrom =? OR userIdTo= ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlForTransfers, userId, userId);
        List<Transfer> transfers = new ArrayList<>();
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        System.out.println(transfers);
        return transfers;
    }

    public Transfer viewTransferById(int id) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet transferInfo = jdbcTemplate.queryForRowSet(sql, id);
        Transfer transfer = new Transfer();
        if (transferInfo.next()) {
            transfer = mapRowToTransfer(transferInfo);
        }
        return transfer;

    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setStatus(rowSet.getString("status"));
        transfer.setUserIdFrom(rowSet.getInt("userIdFrom"));
        transfer.setUserIdTo(rowSet.getInt("userIdTo"));
        return transfer;
    }


}
