package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private static final User USER_1 = new User(1001, "bob","$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2");
    private static final User USER_2 = new User(1002, "user","$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy");

    private static final Transfer TRANSFER_1 = new Transfer(BigDecimal.valueOf(50), 1001, 1002);


    private JdbcUserDao sut;
    private User testUser;
    private JdbcAccountDao accountDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        accountDao = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
        Account testAccount = accountDao.getAccount("TEST_USER");

        Account expectedAccount = new Account(2003, 1003);
        assertAccountsMatch(testAccount, expectedAccount);

    }

    @Test
    public void getUserNamesForTransfer(){
        String username = USER_1.getUsername();
        List<User> actualUsers = sut.getUserNamesForTransfer(username);

        assertEquals(1, actualUsers.size());
        assertUsersMatch(USER_2, actualUsers.get(0));
    }

    private void assertAccountsMatch(Account expected, Account actual){
        assertEquals(expected.getAccount_id(), actual.getAccount_id());
        assertEquals(expected.getUser_id(), actual.getUser_id());
        assertTrue(expected.getBalance().compareTo(actual.getBalance())==0);

    }

    private void assertUsersMatch(User expected, User actual){
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getId(), actual.getId());
    }



}
