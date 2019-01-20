package com.example.email.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.email.model.User;
import com.example.email.services.UserService;

@RestController
public class UserController {
  UserService UserService;

  @Autowired
  public UserController(UserService UserService) {
    this.UserService = UserService;
  }

  @RequestMapping(value = {"user"}, method = RequestMethod.GET)
  public @ResponseBody User getUser(@RequestParam String emailId) {
    User user = UserService.getUser(emailId);
    return user;
  }

  @RequestMapping(value = {"verify"}, method = RequestMethod.POST)
  public boolean getUser(@RequestBody User user) {
    return UserService.verifyCredential(user);
  }
}
