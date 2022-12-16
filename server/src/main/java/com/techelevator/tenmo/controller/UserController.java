package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController

@PreAuthorize("isAuthenticated()")
public class UserController {



    private JdbcUserDao userDao;

    public UserController(JdbcUserDao dao) {

        this.userDao = dao;

    }
// Shows user a List of users they can transfer TE bucks to
    @RequestMapping(path = "/accounts/fellow_users", method = RequestMethod.GET)
    public List<User> getAllUsers (Principal principal) {
        String userRequestName = principal.getName();
        List<User> users = userDao.getUserNamesForTransfer(userRequestName);
        return users;
    }



}
