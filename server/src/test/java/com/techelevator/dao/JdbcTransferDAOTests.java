package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JdbcTransferDAOTests extends BaseDaoTests {



    private static final Transfer TRANSFER_1 = new Transfer(3001,BigDecimal.valueOf(50), 1001, 1002);
    private static final Transfer TRANSFER_2 = new Transfer(3002,BigDecimal.valueOf(250.67), 1001, 1002);
    private static final Transfer TRANSFER_3 = new Transfer(3003,BigDecimal.valueOf(300), 1003, 1002);
    private static final Transfer TRANSFER_4 = new Transfer(3004, BigDecimal.valueOf(200), 1003, 1001);


    private static final User USER_3 = new User(1003, "JonWick","$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAZT");
    private static final User USER_1 = new User(1001, "bob","$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2");


    private JdbcTransferDao sut;
    private JdbcAccountDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        dao = new JdbcAccountDao(jdbcTemplate);
    }

    Transfer transfers = new Transfer();

    @Test
    public void getAllTransfers() {
        List<Transfer>  allTransfers = sut.viewMyTransfers("JonWick");
        assertEquals(1, allTransfers.size());
        assertTransferMatch(TRANSFER_3, allTransfers.get(0));
    }

    private void assertTransferMatch(Transfer expected, Transfer actual) {
        assertTrue(expected.getAmount().compareTo(actual.getAmount()) == 0);

        assertEquals(expected.getUserIdFrom(), actual.getUserIdFrom());
        assertEquals(expected.getUserIdTo(), actual.getUserIdTo());
        assertEquals(expected.getTransferId(),actual.getTransferId());


    }
    @Test
    public void viewTransferByIdTest() {
        Transfer transfer = sut.viewTransferById(3003);
        assertTransferMatch(TRANSFER_3, transfer);
    }

    @Test
    public void transferWorksCorrectly() {                  //200
        String transferStatement = sut.transfer(TRANSFER_4.getAmount(),TRANSFER_4.getUserIdFrom(),TRANSFER_4.getUserIdTo(), "Approved");
        Transfer  transfer = sut.viewTransferById(TRANSFER_4.getTransferId());
        assertTransferMatch(TRANSFER_4, transfer);
                                       //800
     BigDecimal JonWickBalance = dao.getBalance(USER_3.getUsername());
                                      //1200
     BigDecimal bobBalance = dao.getBalance(USER_1.getUsername());
     assertTrue(bobBalance.compareTo(BigDecimal.valueOf(1200))==0);

    assertTrue(JonWickBalance.compareTo(BigDecimal.valueOf(800))==0);

    }
}
