package com.example.email.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.email.dao.client.UserClient;
import com.example.email.model.User;
import com.example.email.utils.MD5;

@Service
public class UserServiceImpl implements UserService {

  private final UserClient userClient;

  @Autowired
  public UserServiceImpl(UserClient userClient) {
    this.userClient = userClient;
  }

  @Override
  public User getUser(String emailId) {
    return userClient.getUser(emailId);
  }

  @Override
  public boolean verifyCredential(User user) {
    boolean success = false;
    User userInDb = userClient.getUser(user.getEmailId());
    if (MD5.getMd5(user.getPassword()).equals(userInDb.getPassword())) {
      success = true;
    }
    return success;
  }
}
