package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;


@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

private JdbcUserDao userDao;
    public UserController(JdbcUserDao dao){
        this.userDao = dao;

    }
@RequestMapping(path = "account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
    String username = principal.getName();
    String sql = "SELECT * FROM tenmo_user WHERE user_name = ?";
    User user = userDao.findByUsername(username);

    return null;
}

}
