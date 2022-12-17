package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class JdbcAcountDaoTests extends BaseDaoTests{

    private static final User USER_1 = new User(1001, "bob","$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2");
    private static final User USER_2 = new User(1002, "user","$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy");
    private static final User USER_3 = new User(1003, "JonWick","$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAZT");



    private static final Account ACCOUNT_1 = new Account(2001, 1001);
    private static final Account ACCOUNT_2 = new Account(2002, 1002);


    private JdbcAccountDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getBalance_works_correctly(){
        BigDecimal actualBalance = sut.getBalance(USER_2.getUsername());
        System.out.println(actualBalance);

        BigDecimal expectedBalance = ACCOUNT_2.getBalance();
        System.out.println(expectedBalance);
        assertTrue(expectedBalance.compareTo(actualBalance)==0);

    }

}
